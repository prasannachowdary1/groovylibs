// USAGE:
// include this file in consumers who need it like so
// def artifactory = load('groovy/artifactory.groovy')


def artifactory_upload() {

try {
      echo "===================================================="
      echo "@@@@@@@@@@ Artifact Upload to Artifactory @@@@@@@@@@@"
      echo "===================================================="
    
    if (env.ENVIRONMENT) {
      env.artifactName = "${env.PROJECT_NAME}-${env.ENVIRONMENT}-${env.BUILD_NUMBER}.${ARCHIVE_TYPE}"
    } else {
      env.artifactName = "${env.PROJECT_NAME}-${env.BUILD_NUMBER}.${ARCHIVE_TYPE}"
    }
    env.artifactoryUrl = "http://54.218.44.118:8081/artifactory/"

     def artifactoryServer = Artifactory.newServer( url: "${artifactoryUrl}", credentialsId: 'jenkins-artifactory-cred' )

     artifactoryServer.setBypassProxy(true)

     def artifactUploadSpec = """
     {
       "files": [
        {
          "pattern": "*.${Archive_Type}",
          "target": "${env.targetDir}/${env.artifactName}"
        }
      ]
    }
     
     """

      echo "*******************************************"
      echo "********** Verify the Variables ***********"
      echo "*******************************************"

      echo "Target Path = ${targetDir}"
      echo "Artifactory URL = ${artifactoryUrl}"
      echo "Upload Spec File = ${artifactUploadSpec}"

      artifactoryServer.upload("${artifactUploadSpec}")
    }
  
  catch(e) {
     throw e
  }
}

def artifactory_download() {

try {
      echo "===================================================="
      echo "@@@@@@@@@@ Artifact Download From Artifactory @@@@@@@@@@@"
      echo "===================================================="
    
    if (env.ENVIRONMENT) {
      env.artifactName = "${env.PROJECT_NAME}-${env.ENVIRONMENT}-${env.BUILD_NUMBER}.${ARCHIVE_TYPE}"
    } else {
      env.artifactName = "${env.PROJECT_NAME}-${env.BUILD_NUMBER}.${ARCHIVE_TYPE}"
    }

    env.artifactoryUrl = "http://54.218.44.118:8081/artifactory/"

     def artifactoryServer = Artifactory.newServer( url: "${artifactoryUrl}", credentialsId: 'jenkins-artifactory-cred' )

     artifactoryServer.setBypassProxy(true)

     def downloadSpec = """
     {
       "files": [
        {
          "pattern": "${env.targetDir}/${env.artifactName}",
          "target": "${env.local_target}"
        }
      ]
    }
     
     """

      echo "*******************************************"
      echo "********** Verify Variables ***********"
      echo "*******************************************"

      echo "Target Path = ${targetDir}"
      echo "Artifactory URL = ${artifactoryUrl}"
      echo "Upload Spec File = ${downloadSpec}"

      artifactoryServer.download("${downloadSpec}")
    }
  
  catch(e) {
     throw e
  }
}

return this
