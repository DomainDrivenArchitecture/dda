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

### What is the difference between roles and clusterroles?
* "Both consist of rules. The difference between a Role and a ClusterRole is the scope: in a Role, the rules are applicable to a single namespace, whereas a ClusterRole is cluster-wide, so the rules are applicable to more than one namespace. ClusterRoles can define rules for cluster-scoped resources (such as nodes) as well. Both Roles and ClusterRoles are mapped as API Resources inside our cluster."
* [Source](https://docs.bitnami.com/kubernetes/how-to/configure-rbac-in-your-kubernetes-cluster/)

### If we expose the Dashboard to the internet (which we want for control), is there more to do than just secure the login?
* [https://github.com/kubernetes/dashboard/wiki/Accessing-Dashboard---1.7.X-and-above](https://github.com/kubernetes/dashboard/wiki/Accessing-Dashboard---1.7.X-and-above)

### How can we inject users for dashboard?

### How can users log in to the dashboard?
* Authorization: Bearer <token> header passed in every request to Dashboard. If present, login view will not be shown.
* Bearer Token that can be used on Dashboard login view.
* Username/password that can be used on Dashboard login view.
* Kubeconfig file that can be used on Dashboard login view.

### How can we deactivate default access to the dashboard? 
* Looking at: https://kubernetes.io/docs/reference/access-authn-authz/rbac/#discovery-roles -> seems to be no discovery role on microk8s
* "Using Skip option will make Dashboard use privileges of Service Account used by Dashboard" -> looking at restricting Dasboard Service Account?
* Default Service Account exists for alle Namespaces and pods take it when they dont have another account -> need to restrict it too 

### How is the role of the dashboard service account determined?
* Could not find a cluster/rolebinding for the default dashboard service account -> unclear which role it even has
* Probably just make new rolebinding? 
* Dashboard config: https://github.com/ubuntu/microk8s/blob/master/microk8s-resources/actions/dashboard.yaml
* Is it possible that with the configured certs, that the dashboard does not need a role?

### Is the API Server exposed to the internet in default settings? How can we deactivate that/deactivate anonymous user calls?

### How can we hand over certs ?
Inputs for Certs can be the letsencrypt controller pod or the static configured certs. How the app-ingress can use such a cert?

### How are secrets protected against pods ?

### How can we prevent pods from doing kubectl/api calls? (in general)
* Every pod has a service account, give it lower role?

### Is the API servers authorization mode AlwaysAllow suited for our needs?
* https://github.com/ubuntu/microk8s/blob/master/microk8s-resources/default-args/kube-apiserver
* Might want to change default args -> New microk8s build?

### Which roles / actions are available on default k8s ?
* see also: 
  * [RBAC explained](https://www.cncf.io/blog/2018/08/01/demystifying-rbac-in-kubernetes/)
  * [Pod Security Policies](https://medium.com/coryodaniel/kubernetes-assigning-pod-security-policies-with-rbac-2ad2e847c754)


## Decission
...
