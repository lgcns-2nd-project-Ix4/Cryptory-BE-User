pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-northeast-1'

        // ECR 정보
        ECR_REGISTRY = '050314037804.dkr.ecr.ap-northeast-1.amazonaws.com'
        ECR_REPO = "${ECR_REGISTRY}/be-user"
        IMAGE_NAME = 'be-user'

        // ECS 정보
        ECS_CLUSTER = 'Ix4-be-cluster'
        ECS_SERVICE = 'be-user-service'
        TASK_DEFINITION_NAME = 'task-definition-BE-User'
        CONTAINER_NAME = 'be-user-container'
        EXECUTION_ROLE_ARN = 'arn:aws:iam::050314037804:role/ecsTaskExecutionRole'
        DESIRED_COUNT = 1
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/lgcns-2nd-project-Ix4/Cryptory-BE-User.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh """
                    docker build -t $IMAGE_NAME .
                    docker tag $IMAGE_NAME:latest $ECR_REPO:latest
                """
            }
        }

        stage('Login to ECR') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'AWS-CREDENTIALS'
                ]]) {
                    sh """
                        aws ecr get-login-password --region $AWS_REGION | \
                        docker login --username AWS --password-stdin $ECR_REGISTRY
                    """
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                sh "docker push $ECR_REPO:latest"
            }
        }

        stage('Register ECS Task Definition') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'AWS-CREDENTIALS'
                ]]) {
                    script {
                        def registerOutput = sh(
                            script: """
                                aws ecs register-task-definition \
                                    --family $TASK_DEFINITION_NAME \
                                    --requires-compatibilities FARGATE \
                                    --network-mode awsvpc \
                                    --execution-role-arn $EXECUTION_ROLE_ARN \\
                                    --cpu "256" \
                                    --memory "512" \
                                    --container-definitions '[
                                        {
                                            "name": "$CONTAINER_NAME",
                                            "image": "$ECR_REPO:latest",
                                            "essential": true,
                                            "portMappings": [
                                                {
                                                    "containerPort": 8080,
                                                    "hostPort": 8080,
                                                    "protocol": "tcp"
                                                }
                                            ],
                                            "logConfiguration": {
                                                "logDriver": "awslogs",
                                                "options": {
                                                  "awslogs-group": "/ecs/task-definition-BE-User",
                                                  "awslogs-region": "ap-northeast-1",
                                                  "awslogs-stream-prefix": "ecs"
                                                }
                                            },
                                            "environment": [
                                            	{
                                            		"name":"ACCESS_EXPIRATION",
                                            		"value": "${env.ACCESS_EXPIRATION}"
                                            	},
                                            	{
                                            		"name":"BASE_URL",
                                            		"value": "http://ix4-fe-bucket.s3-website-ap-northeast-1.amazonaws.com/"
                                            	},
                                            	{
                                            		"name":"CONFIG_SERVER_URL",
                                            		"value": "${env.CONFIG_SERVER_URL}"
                                            	},
                                            	{
                                            		"name":"EUREKA_URL",
                                            		"value": "${env.EUREKA_URL}"
                                            	},
                                            	{
                                            		"name":"GIT_PASSWORD",
                                            		"value": "${env.GIT_PASSWORD}"
                                            	},
                                            	{
                                            		"name":"GIT_USERNAME",
                                            		"value": "${env.GIT_USERNAME}"
                                            	},
                                            	{
                                            		"name":"KAKAO_CLIENT_ID",
                                            		"value": "${env.KAKAO_CLIENT_ID}"
                                            	},
                                            	{
                                            		"name":"KAKAO_SECRET",
                                            		"value": "${env.KAKAO_SECRET}"
                                            	},
                                            	{
                                            		"name":"MYSQL_DATABASE",
                                            		"value": "${env.MYSQL_DATABASE}"
                                            	},
                                            	{
                                            		"name":"MYSQL_HOST",
                                            		"value": "${env.MYSQL_HOST}"
                                            	},
                                            	{
                                            		"name":"MYSQL_PASSWORD",
                                            		"value": "${env.MYSQL_PASSWORD}"
                                            	},
                                            	{
                                            		"name":"MYSQL_PORT",
                                            		"value": "${env.MYSQL_PORT}"
                                            	},
                                            	{
                                            		"name":"MYSQL_URL",
                                            		"value": "${env.MYSQL_URL}"
                                            	},
                                            	{
                                            		"name":"MYSQL_USERNAME",
                                            		"value": "${env.MYSQL_USERNAME}"
                                            	},
                                            	{
                                            		"name":"NAVER_CLIENT_ID",
                                            		"value": "${env.NAVER_CLIENT_ID}"
                                            	},
                                            	{
                                            		"name":"NAVER_SECRET",
                                            		"value": "${env.NAVER_SECRET}"
                                            	},
                                            	{
                                            		"name":"OPENAI_API_KEY",
                                            		"value": "${env.OPENAI_API_KEY}"
                                            	},
                                            	{
                                            		"name":"RABBITMQ_HOST",
                                            		"value": "${env.RABBITMQ_HOST}"
                                            	},
                                            	{
                                            		"name":"RABBITMQ_PASSWORD",
                                            		"value": "${env.RABBITMQ_PASSWORD}"
                                            	},
                                            	{
                                            		"name":"RABBITMQ_PORT",
                                            		"value": "${env.RABBITMQ_PORT}"
                                            	},
                                            	{
                                            		"name":"RABBITMQ_USERNAME",
                                            		"value": "${env.RABBITMQ_USERNAME}"
                                            	},
                                            	{
                                            		"name":"SECRET_KEY",
                                            		"value": "${env.SECRET_KEY}"
                                            	},
                                            	{
                                            		"name":"SERPAPI_KEY",
                                            		"value": "${env.SERPAPI_KEY}"
                                            	},
                                            	{
                                                    "name": "ENV_ACTIVE",
                                                    "value": "NO"
                                                },
                                                {
                                                    "name": "SPRING_PROFILE_ACTIVE",
                                                    "value": "docker"
                                                }
                                            ]
                                        }
                                    ]' \
                                    --region $AWS_REGION \
                                    --output json
                            """,
                            returnStdout: true
                        ).trim()

                        def taskDefArn = new groovy.json.JsonSlurperClassic()
                            .parseText(registerOutput)
                            .taskDefinition
                            .taskDefinitionArn

                        env.TASK_DEF_ARN = taskDefArn
                    }
                }
            }
        }

        stage('Deploy to ECS') {
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'AWS-CREDENTIALS'
                ]]) {
                    sh """
                        aws ecs update-service \
                            --cluster $ECS_CLUSTER \
                            --service $ECS_SERVICE \
                            --task-definition $TASK_DEF_ARN \
                            --desired-count $DESIRED_COUNT \
                            --force-new-deployment \
                            --region $AWS_REGION
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ ECS service successfully created or updated!"
        }
        failure {
            echo "❌ ECS service creation or update failed!"
        }
    }
}