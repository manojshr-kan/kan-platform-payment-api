# payment-api — GitHub & AWS Setup

## 1. GitHub repository variables

Settings → Secrets and variables → Actions → **Variables**:

| Variable | Value |
|----------|-------|
| `AWS_ROLE_ARN` | `arn:aws:iam::<account>:role/kan-cloud-platform-github-actions` |
| `AWS_REGION` | `ap-south-1` |

Authentication to AWS is via OIDC — no secrets needed.

## 2. OIDC trust (one-time, in Terraform)

Listed in `kan-cloud-platform-terraform/iam-oidc.tf`:

```
repo:manojshr-kan/kan-platform-payment-api:*
```

## 3. Prerequisites provisioned by Terraform

- ECR repository `payment-api`
- IRSA role `payment-api-irsa`
- SQS queue `payment-api-queue`, SNS topic, Secrets Manager secret `payment-api/config`
- Cognito `payment-api` app client

## 4. CI/CD flow

```
PR opened     -> reusable java-build-test runs (mvn verify)
merge to main -> reusable build-and-push pushes image to ECR
              -> PR in kan-cloud-platform-gitops bumps the image tag
              -> Argo CD syncs into EKS dev
```

## 5. Populate the secret (manual, one-time)

```bash
aws secretsmanager put-secret-value \
  --secret-id payment-api/config \
  --secret-string '{"example.key":"example-value"}'
```

## 6. Verify in cluster

```bash
kubectl -n applications port-forward svc/payment-api 8080:80
curl localhost:8080/health     # expect 200 {"status":"UP",...}
```
