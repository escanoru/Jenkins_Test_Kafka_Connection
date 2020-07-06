def setDescription() {
  def item = Jenkins.instance.getItemByFullName(env.JOB_NAME) 
  item.setDescription("<h2 style=\"color:#138D75\">Job to test the connection to a given kafka cluter, 10 messages will be sent, if the 1st message fails to be delivered then the job will fail.</h2>") 
  item.save()
  }
setDescription()

// Declarative //
pipeline {
	agent { label 'jmeter_slave' }
	options {
		ansiColor('xterm')
		buildDiscarder(logRotator(daysToKeepStr: '180'))
		}
  parameters {
        string(
		name: 'Kafka_Brokers_PORT',
		defaultValue: '15.214.',
		description: '<h4 style=\"color:#117864\">Kafka_Brokers:PORT separated by comma, e.g 10.0.0.2:9092, 10.0.0.3:9092, 10.0.0.4:9092, 10.0.0.5:9092</h4>'
		)
        string(
		name: 'TOPIC',
		defaultValue: 'FROM_JENKINS',
		description: '<h4 style=\"color:#117864\">Target topic name.</h4>'
		)
  }
	
    stages {
        stage('Sending Messages') {
            steps {
			    sh '''
				for item in {1..10} ; do echo -e \"\nSending Message to the provider kafka cluster\" ; /opt/GO_kafka-simple-producer_linux -brokers "${Kafka_Brokers_PORT}" -topic "${TOPIC}" -value "Testing connection from Jenkins" ; done
				echo -e "\n\n\nKafka cluster is up \n\n\n" > /dev/null
				'''	
                }
            }
        }
	
  post {
      always {
          echo 'Clenning up the workspace'
          deleteDir()
      }
  }
  
}