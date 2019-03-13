# Architecture for K8s

## Draft of idea
### Requirements
* a small k8s all in one system für serving one application.
* is compatible with k8s
* providing ingress for app to be installed (replacement of traditional reverse-proxy httpd)
* support letsencrypt (dynamic created by https) for a defined fqdn or injected static https certs.
* exposes dashboard with users defined & disabled annonymous access.

### Architecture
<img src="content/uploads/K8s_wiring.jpg" width="80%" alt="K8s wiring">

## Discussion

### How can we hand over certs ?
Inputs for Certs can be the letsencrypt controller pod or the static configured certs. How the app-ingress can use such a cert?

### How are secrets protected against pods ?

### Which roles / actions are available on default k8s ?
* see also: 
  * [RBAC explained](https://www.cncf.io/blog/2018/08/01/demystifying-rbac-in-kubernetes/)
  * [Pod Security Policies](https://medium.com/coryodaniel/kubernetes-assigning-pod-security-policies-with-rbac-2ad2e847c754)


### Applying Resource
Application of generic resource ([Source](https://kubernetes.io/docs/reference/access-authn-authz/rbac/)): `kubectl create -f (resource).yml`

It might be better to fork the SNAP and apply the config directly at the start than use this approach. 

#### Empty Role YAML
```
kind: Role
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
	name: test
```

#### Empty Rolebinding YAML
```
kind: RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: test
  namespace: kube-system
subjects:
- kind: User
  name: 
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: test
  apiGroup: rbac.authorization.k8s.io
```

### Securing microk8s
To secure microk8s we need to secure all its parts and to do that we need to understand and edit the standard config. This is critical as the standard config is highly insecure from what we understood so far. 

Generally it would be a good idea to study the [repo](https://github.com/ubuntu/microk8s). 

#### Default Args
Starting point are [the default args](https://github.com/ubuntu/microk8s/tree/master/microk8s-resources/default-args) for each of the used pods. 

If we want to edit it live and not in the SNAP itself, we can edit the respective files on the serverpath "/var/snap/microk8s/current" and then restart microk8s. 

#### Default NS
For every Namespace there exists are Default User, which is taken by a POD if it does not have another one. So this needs to be restricted for the Namespaces we want to use.

#### ServiceAccounts
Each pod has the rights of its associated ServiceAccount.
If we are using RBAC we can restrict the ServiceAccount by creating a [Cluster/Rolebinding](https://kubernetes.io/docs/reference/access-authn-authz/rbac/).

#### DiscoveryRoles
There exist an option for unauthorized and unauthenticated access to the API with so-called [Discovery Roles](https://kubernetes.io/docs/reference/access-authn-authz/rbac/#discovery-roles) (if we are using RBAC), this should be restricted.

### Securing the kube-API Server 

Standard Authorization Mode is AlwaysAllow. This obviously needs to be changed. 

1. Change Authorization Mode from AlwaysAllow to AlwaysAllow,AlwaysDeny,ABAC,Webhook,RBAC,Node [Source](https://kubernetes.io/docs/reference/command-line-tools-reference/kube-apiserver/)
2. For Example: To activate RBAC replace "--authorization-mode=AlwaysAllow" with "--authorization-mode=RBAC" in line 5 of the file "/var/snap/microk8s/current/args/kube-apiserver"
3. Restart microk8s
4. ...

### Securing the Dashboard
In general we have a quite different vision for our Dashboard Security than is expected in [the standard manual](https://github.com/kubernetes/dashboard/wiki/Accessing-Dashboard---1.7.X-and-above) if we want to expose it to the public/make it easy to access through the Browser.

#### How can we inject users for dashboard?
* one possibility is to create a new ServiceAccount, make the appropriate RoleBinding. A Bearer token is automatically created and can be used to login.

#### How can users log in to the dashboard?
* Authorization: Bearer token header passed in every request to Dashboard. If present, login view will not be shown.
* Bearer Token that can be used on Dashboard login view.
* Username/password that can be used on Dashboard login view.
* Kubeconfig file that can be used on Dashboard login view

#### Version 1: Securely Exposing the Dashboard to the Public 
The Skip Button makes the Dashboard use its ServiceAccount to log in. This means that the person who skipped login can access everything that the Dashboard-Account can access.

Currently microk8s uses the Dashboard Version 1.8.x that does not support the disabling of the Skip Button (which currently is the main threat). The newer Dashboard Versions disable the Skip Button by default ([Source](https://github.com/kubernetes/dashboard/issues/2672))
If we want to repackage the SNAP anyways we could just replace the used version [in the deployment config of the Dashboard.](https://github.com/ubuntu/microk8s/blob/master/microk8s-resources/actions/dashboard.yaml)

The current idea is that we could maybe restrict the Dashboard-ServiceAccount so much that it would be useless to Skip as it would not yield any information. However, there seems to be an issue with restricting it too much and the Dashboard not showing at all because of that. 

Current Config Steps would be:
1. Assuming RBAC: Create ServiceAccount for Dashboard POD with just enough rights for the Dashboard to start (but not see any data)
2. Dashboard.json apply Rolebinding
3. Create (Service)Account for each user that needs access and use its Bearer token for accessing the Dashboard
4. ...

Open Question: Is it even possible to make a Role that allows the Dashboard not to see any data but still run?
Next step: Make empty Role and see if the Dashboard can be accessed at all

#### Version 2: Not Exposing the Dashboard
Alternative Security Approach ([Source](https://blog.heptio.com/on-securing-the-kubernetes-dashboard-16b09b1b7aca)): 
Not expose the Dashboard to the public, because maybe its not possible to do it without exposing any data (see Open Question) and this would additionally shield us from Dashboard/Config Bugs. In the article he does this with a kubectl proxy which requires local setup of kubectl and authorization.
This might be the preferred version but also not user-friendly, if we want to allow access to our Dashboard to a third party.

## Decision
* Accessing Dashboard only via Proxy and restricting it to view only
* Activating RBAC 
* Exposing only the specific pod (e.g. Nexus) and securing the POD itself and its ServiceAccount
* Researching more security issues of microk8s
