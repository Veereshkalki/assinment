boolean isX=true
pipeline{
    agent any
    parameters {
        choice choices: ['success', 'fail'], description: 'set \'X\' stage status', name: 'Is_X'
    }

    stages{
        stage('X'){
            steps{
            script{
                try{
                echo 'Stage X is Running'
                if(Is_X == 'fail'){
                    sh 'exit 1'
                }else{
                    sh 'exit 0'
                }
            }catch(Exception e){
                echo 'Stage X Failed'
                isX=false
            }
            }
        }
        }

        stage('Y'){
            when {
                expression {
                    isX == true
                }
            }
            steps{
                echo 'Stage Y is running'
            }
        }

        stage('Z'){
            when {
                expression {
                    isX == false
                }
            }
            steps{
                echo 'Stage Z is running'
            }
        }
    }
}