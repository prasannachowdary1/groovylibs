// USAGE:
// include this file in consumers who need it like so
// def artifactory = load('groovy/artifactory.groovy')


def artifactory_upload() {

try {
      echo "===================================================="
      echo "@@@@@@@@@@ Artifact Upload to Artifactory @@@@@@@@@@@"
      echo "===================================================="
    
    env.artifactoryUrl = "http://54.218.44.118:8081/artifactory/"

     def artifactoryServer = Artifactory.newServer( url: "${artifactoryUrl}", credentialsId: 'jenkins-artifactory-creds' )

     def artifactUploadSpec = """
     {
       "files": [
        {
          "pattern": "*.${Archive_Type}",
          "target": "${env.targetDir}"
        }
      ]
    }
     
     """

      echo "*******************************************"
      echo "********** Verify the Variables ***********"
      echo "*******************************************"

      echo "Artifact Name = ${env.Artifact_Name}"
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
    
    env.artifactoryUrl = "https://artifactory.aws.foxinc.com/artifactory/"

     def artifactoryServer = Artifactory.newServer( url: "${artifactoryUrl}", credentialsId: 'jenkins-artifactory-creds' )

     artifactoryServer.setBypassProxy(true)

     def downloadSpec = """
     {
       "files": [
        {
          "pattern": "${env.targetDir}/${env.Artifact_Name}",
          "target": "${PWD}"
        }
      ]
    }
     
     """

      echo "*******************************************"
      echo "********** Verify the Variables ***********"
      echo "*******************************************"

      echo "Artifact Name = ${env.Artifact_Name}"
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
