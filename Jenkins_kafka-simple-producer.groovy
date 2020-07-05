def setDescription() {
  def item = Jenkins.instance.getItemByFullName(env.JOB_NAME) 
  item.setDescription("<h2 style=\"color:#138D75 \">Job to 10 messages to test if a given kafka cluter is available</h2>") 
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
		description: '<h4>HOST:PORT separated by comma, e.g 10.0.0.2:9092, 10.0.0.3:9092, 10.0.0.4:9092, 10.0.0.5:9092</h4>'
		)
        string(
		name: 'TOPIC',
		defaultValue: 'EVENTS_FROM_JENKINS',
		description: '<h4>Target topic. By default the job will create he the topic \"EVENTS_FROM_JENKINS\" and then send the events.</h4>'
		)
  }
	
    stages {
        stage('Sending Messages') {
            steps {
			    sh '''
				for item in {1..10} ; do echo -e \"\nSending Message to the provider kafka cluster\" ; /opt/GO_kafka-simple-producer_linux -brokers "${Kafka_Brokers_PORT}" -topic "${TOPIC}" -value "Testing connection from Jenkins" ; done
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