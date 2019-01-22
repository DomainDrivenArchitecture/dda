# Architecture for K8s

## Draft of idea
### Requirements
* a small k8s all in one system für serving one application.
* is compatible with k8s
* providing ingress for app to be installed (replacement of traditional reverse-proxy httpd)
* support letsencrypt (dynamic created by https) for a defined fqdn or injected static https certs.
* exposes dashboard with users defined

### Architecture
<img src="content/uploads/K8s_wiring.jpg" width="80%" alt="K8s wiring">

## Discussion
### How can we inject users for dashboard?

### How can we hand over certs ?
Inputs for Certs can be the letsencrypt controller pod or the static configured certs. How the app-ingress can use such a cert?

### How are secrets protected against pods ?


## Decission
...
