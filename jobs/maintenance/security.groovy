String basePath = 'maintenance'
String repo = 'omgbebebe/jenauto'

job("$basePath/auth") {
    scm { github repo }
    steps {
      systemGroovyCommand('''
      import jenkins.*
      import hudson.*
      import com.cloudbees.plugins.credentials.*
      import com.cloudbees.plugins.credentials.common.*
      import com.cloudbees.plugins.credentials.domains.*
      import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
      import hudson.plugins.sshslaves.*;
      import hudson.model.*
      import jenkins.model.*
      import hudson.security.*
      import org.yaml.snakeyaml.*
      import org.jenkinsci.plugins.googlelogin.*

      global_domain = Domain.global()
      credentials_store =
        Jenkins.instance.getExtensionList(
         'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
        )[0].getStore()

      credentials = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL,null,"root",new BasicSSHUserPrivateKey.UsersPrivateKeySource(),"","")

      credentials_store.addCredentials(global_domain, credentials)
      ''') {classpath('/var/lib/jenkins/third_party/snakeyaml-1.17.jar')
    }
}
