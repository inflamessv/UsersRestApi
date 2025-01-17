pipeline{
    agent any
    environment {
        IMAGE_NAME = "users-rest-api"
        CONTAINER_NAME = "users-api"
    }
    stages {
        stage("Clonar codigo"){
            step{
                //clona el repo
                git branch: 'master', url: 'https://github.com/inflamessv/APIProduct.git'
            }
        }
        stage("Compilar y empaquetar"){
            step{
                //compila y empaqueta
                sh './mvn clean package'
            }
        }
        stage("Construir imagen docker"){
            step{
                //contruye imagen docker de dockerfile
                sh 'docker build -t $IMAGE_NAME .'
            }
        }
        stage("Desplegar contenedor local"){
            step{
                //crea nuevo contenedor
                sh 'docker run -d --name $CONTAINER_NAME -p 8081:8081 $IMAGE_NAME'
            }
        }
    }
}