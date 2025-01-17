pipeline{
    agent any
    environment {
        IMAGE_NAME = "users-rest-api"
        CONTAINER_NAME = "users-api"
    }
    stages {
        stage("Clonar codigo"){
            steps {
                checkout([
                        $class           : 'GitSCM',
                        branches         : [[name: '*/main']],
                        userRemoteConfigs: [[url: 'https://github.com/usuario/springboot-api.git']],
                        extensions       : [[$class: 'CleanCheckout'], [$class: 'CloneOption', noTags: false, shallow: false]]
                ])
            }
        }
        stage("Compilar y empaquetar"){
            steps{
                //compila y empaqueta
                sh '''
                    chmod +x mvnw
                    ./mvnw clean package
                '''
            }
        }
        stage("Construir imagen docker"){
            steps{
                //contruye imagen docker de dockerfile
                sh '''
                    pwd
                    ls -l
                    docker build -t $IMAGE_NAME .
                '''
            }
        }
        stage("Desplegar contenedor local"){
            steps{
                //crea nuevo contenedor
                sh 'docker run -d --name $CONTAINER_NAME -p 8081:8081 $IMAGE_NAME'
            }
        }
    }
}