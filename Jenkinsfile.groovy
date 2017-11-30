node{
    echo "Pipeline script example"
    writeFile file: 'sample.txt', text: 'hellow'
    bat'''
        echo 'line 2' >> sample.txt
    '''
    echo 'reading file'
    def readContent = readFile 'sample.txt'
    
    println(readContent)

    stage("code check out"){
        println("Code checkout in this stage")
        git 'https://github.com/EMERDs/SampleProj.git'
    }
    stage("maven buld"){
        println("Maven build is running")
        bat 'mvn clean install -DReleaseVersion=2.0.0'
    }
    stage("deploy"){
        println("deployi9ng the package to tomcat server or target environment")
    }
    parallel(
        "UI Tests":{
            stage("UI Tests"){
                println("UI Test cases are running")
            }
        },
        "API Tests":{
            stage("API Tests"){
                println("API Test cases are running")
            }
        },
        "Functional Tests":{
            stage("Functional Tests"){
                println("Functional Test cases are running")
            }
        }
    )
    stage("email notification"){
        println("email will be sent to the dev team with buikd result")
        //mail attachment: "", body: 'Build status', subject: 'Build status', to: 'abc@gmail.com'
    }
}
