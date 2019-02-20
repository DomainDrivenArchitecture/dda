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

### Securing the kube-API Server 
1. Change Authorization Mode from AlwaysAllow to AlwaysAllow,AlwaysDeny,ABAC,Webhook,RBAC,Node [Source](https://kubernetes.io/docs/reference/command-line-tools-reference/kube-apiserver/)
2. For Example: To activate RBAC replace "--authorization-mode=AlwaysAllow" with "--authorization-mode=RBAC" in line 5 of the file "/var/snap/microk8s/current/args/kube-apiserver"
3. Restart microk8s
4. ...

### Securing the Dashboard
Currently microk8s uses the Dashboard Version 1.8.x that does not support the disabeling of the Skip Button (which currently is the main threat). The newer Dashboard Versions disable the Skip Button by default ([Source](https://github.com/kubernetes/dashboard/issues/2672))

1. Assuming RBAC: Create ServiceAccount for Dashboard POD with just enough rights for the Dashboard to start (but not see any data)
2. Dashboard.json apply Rolebinding
3. Create (Service)Account for each user that needs access and use its Bearer token for accessing the Dashboard
4. ...

Open Question: Is it even possible to make a Role that allows the Dashboard not to see any data but still run?
Next step: Make empty Role and see if the Dashboard can be accessed at all

Alternative Security Approach ([Source](https://blog.heptio.com/on-securing-the-kubernetes-dashboard-16b09b1b7aca)): Not expose the Dashboard to the public, because maybe its not possible to do it without exposing any data (see Open Question) and this will shield us from Dashboard/Config Bugs. In the article he does this with a kubectl proxy which requires local setup of kubectl and authorization . 

### The default config for the microk8s is highly insecure and needs to be changed
* Starting point is default args given to the system services: https://github.com/ubuntu/microk8s/tree/master/microk8s-resources/default-args 
* We need to edit the respective files on the serverpath "/var/snap/microk8s/current" and restart microk8s 
* Default Service Account exists for alle Namespaces and pods take it when they dont have another account -> need to restrict it too 

### To secure the K8S Server we need to secure all the running pods and system services 
* https://github.com/ubuntu/microk8s
* each pod has the rights of its associated ServiceAccount
* to change the rights of a ServiceAccount we need to remove or add Cluster/Rolebindings: https://kubernetes.io/docs/reference/access-authn-authz/rbac/
* https://kubernetes.io/docs/reference/access-authn-authz/rbac/#discovery-roles -> seems to be no discovery role on microk8s

#### Securing the dashboard pod 
* [https://github.com/kubernetes/dashboard/wiki/Accessing-Dashboard---1.7.X-and-above](https://github.com/kubernetes/dashboard/wiki/Accessing-Dashboard---1.7.X-and-above)
* Dashboard config for microk8s: https://github.com/ubuntu/microk8s/blob/master/microk8s-resources/actions/dashboard.yaml

##### How can users log in to the dashboard?
* Authorization: Bearer <token> header passed in every request to Dashboard. If present, login view will not be shown.
* Bearer Token that can be used on Dashboard login view.
* Username/password that can be used on Dashboard login view.
* Kubeconfig file that can be used on Dashboard login view.

##### How can we inject users for dashboard?
* one possibility is to create a new ServiceAccount, make the appropriate RoleBinding. A Bearer token is automatically created and can be used to login

##### How can we deactivate default access to the dashboard? 
* "Using Skip option will make Dashboard use privileges of Service Account used by Dashboard"
* Dashboard has no RoleBinding in microk8s, because all API calls are allowed by default
* After activating RBAC we need to make appropriate RoleBinding for the ServiceAccount -> just enough Permissions to be able to show the login screen and no more than that  

### How can we hand over certs ?
Inputs for Certs can be the letsencrypt controller pod or the static configured certs. How the app-ingress can use such a cert?

### How are secrets protected against pods ?

### Which roles / actions are available on default k8s ?
* see also: 
  * [RBAC explained](https://www.cncf.io/blog/2018/08/01/demystifying-rbac-in-kubernetes/)
  * [Pod Security Policies](https://medium.com/coryodaniel/kubernetes-assigning-pod-security-policies-with-rbac-2ad2e847c754)


## Decision
...
