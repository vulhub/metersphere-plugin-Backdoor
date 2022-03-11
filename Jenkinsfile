pipeline {
    agent {
        node {
            label 'metersphere'
        }
    }
    options {
        quietPeriod(30)
    }
    environment {
        BRANCH_NAME = "master"
    }
    stages {
        stage('Preparation') {
            steps {
                script {
                    RELEASE = ""
                    if (env.TAG_NAME != null) {
                        RELEASE = env.TAG_NAME
                    } else {
                        RELEASE = env.BRANCH_NAME
                    }
                    env.RELEASE = "${RELEASE}"
                    echo "RELEASE=${RELEASE}"
                }
            }
        }

        stage('Build/Test') {
            steps {
                configFileProvider([configFile(fileId: 'metersphere-maven', targetLocation: 'settings.xml')]) {
                    sh "mvn clean package --settings ./settings.xml"
                }
            }
        }
        stage('Release') {
            when { tag "*" }
            steps {
                withCredentials([string(credentialsId: 'gitrelease', variable: 'TOKEN')]) {
                    withEnv(["TOKEN=$TOKEN"]) {
                        dir('target') {
                            sh script: '''
                                release=$(curl -XPOST -H "Authorization:token $TOKEN" --data "{\\"tag_name\\": \\"${RELEASE}\\", \\"target_commitish\\": \\"${BRANCH_NAME}\\", \\"name\\": \\"${RELEASE}\\", \\"body\\": \\"\\", \\"draft\\": false, \\"prerelease\\": true}" https://api.github.com/repos/metersphere/metersphere-plugin-DebugSampler/releases)
                                id=$(echo "$release" | sed -n -e \'s/"id":\\ \\([0-9]\\+\\),/\\1/p\' | head -n 1 | sed \'s/[[:blank:]]//g\')
                                curl -XPOST -H "Authorization:token $TOKEN" -H "Content-Type:application/octet-stream" --data-binary @metersphere-plugin-DebugSampler-${RELEASE}-jar-with-all-dependencies.jar https://uploads.github.com/repos/metersphere/metersphere-plugin-DebugSampler/releases/${id}/assets?name=metersphere-plugin-DebugSampler-${RELEASE}-jar-with-all-dependencies.jar
                            '''
                        }
                    }
                }
            }
        }
        stage('Upload') {
            when { anyOf { tag pattern: "^\\d+\\.\\d+\\.\\d+\$", comparator: "REGEXP";tag pattern: "^\\d+\\.\\d+\\.\\d+-lts\$", comparator: "REGEXP"}}
            steps {
                dir('target') {
                    echo "UPLOADING"
                    withCredentials([usernamePassword(credentialsId: 'OSSKEY', passwordVariable: 'SK', usernameVariable: 'AK')]) {
                        sh("java -jar /opt/uploadToOss.jar $AK $SK fit2cloud2-offline-installer tools/metersphere/metersphere-plugin-DebugSampler-${RELEASE}-jar-with-all-dependencies.jar ./metersphere-plugin-DebugSampler-${RELEASE}-jar-with-all-dependencies.jar")
                    }
                }
            }
        }
    }
    post('Notification') {
        always {
            withCredentials([string(credentialsId: 'wechat-bot-webhook', variable: 'WEBHOOK')]) {
                qyWechatNotification failSend: true, mentionedId: '', mentionedMobile: '', webhookUrl: "$WEBHOOK"
            }
        }
    }
}