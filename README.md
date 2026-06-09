# kan-platform-payment-api

A Spring Boot 3 (Java 21) service on the kan-cloud-platform. Built to the same
platform pattern as `order-service` — proving the platform generalizes: nothing
here is reinvented, only the names differ.

## Endpoints

| Method | Path | Auth | Purpose |
|--------|------|------|---------|
| GET | `/health` | public | Liveness/readiness for the ALB |
| GET | `/actuator/health` | public | Kubernetes probes |
| POST | `/payments` | Bearer JWT | Publishes the payment to SQS |

## How it fits the platform

- **Build:** `.github/workflows/ci.yml` calls the reusable workflows in `kan-cloud-platform-ci`.
- **Deploy:** GitOps — promotion PR in `kan-cloud-platform-gitops` bumps the image tag; Argo CD syncs.
- **Identity:** Kubernetes service account annotated with the `payment-api-irsa` role.
- **Config:** `spring-cloud-aws` resolves `payment-api/config` from Secrets Manager.
- **Auth:** validates JWTs against the shared Cognito issuer.

## Run locally

```bash
mvn spring-boot:run
curl localhost:8080/health
```

See `SETUP.md` for the GitHub + AWS configuration required for CI/CD.
