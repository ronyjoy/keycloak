package org.keycloak.models.cache;

import org.keycloak.models.ApplicationModel;
import org.keycloak.models.AuthenticationProviderModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.CredentialValidation;
import org.keycloak.models.OAuthClientModel;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RequiredCredentialModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.SocialLinkModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserCredentialValueModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.models.UsernameLoginFailureModel;
import org.keycloak.models.cache.entities.CachedRealm;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.models.utils.Pbkdf2PasswordEncoder;
import org.keycloak.models.utils.TimeBasedOTP;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class RealmAdapter implements RealmModel {
    protected CachedRealm cached;
    protected CacheModelProvider cacheSession;
    protected RealmModel updated;
    protected KeycloakCache cache;
    protected volatile transient PublicKey publicKey;
    protected volatile transient PrivateKey privateKey;

    public RealmAdapter(CachedRealm cached, CacheModelProvider cacheSession) {
        this.cached = cached;
        this.cacheSession = cacheSession;
    }

    protected void getDelegateForUpdate() {
        if (updated == null) {
            cacheSession.registerRealmInvalidation(getId());
            updated = cacheSession.getDelegate().getRealm(getId());
            if (updated == null) throw new IllegalStateException("Not found in database");
        }
    }

    @Override
    public String getId() {
        if (updated != null) return updated.getId();
        return cached.getId();
    }

    @Override
    public String getName() {
        if (updated != null) return updated.getName();
        return cached.getName();
    }

    @Override
    public void setName(String name) {
        getDelegateForUpdate();
        updated.setName(name);
    }

    @Override
    public boolean isEnabled() {
        if (updated != null) return updated.isEnabled();
        return cached.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        getDelegateForUpdate();
        updated.setEnabled(enabled);
    }

    @Override
    public boolean isSslNotRequired() {
        if (updated != null) return updated.isSslNotRequired();
        return cached.isSslNotRequired();
    }

    @Override
    public void setSslNotRequired(boolean sslNotRequired) {
        getDelegateForUpdate();
        updated.setSslNotRequired(sslNotRequired);
    }

    @Override
    public boolean isRegistrationAllowed() {
        if (updated != null) return updated.isRegistrationAllowed();
        return cached.isRegistrationAllowed();
    }

    @Override
    public void setRegistrationAllowed(boolean registrationAllowed) {
        getDelegateForUpdate();
        updated.setRegistrationAllowed(registrationAllowed);
    }

    @Override
    public boolean isPasswordCredentialGrantAllowed() {
        if (updated != null) return updated.isPasswordCredentialGrantAllowed();
        return cached.isPasswordCredentialGrantAllowed();
    }

    @Override
    public void setPasswordCredentialGrantAllowed(boolean passwordCredentialGrantAllowed) {
        getDelegateForUpdate();
        updated.setPasswordCredentialGrantAllowed(passwordCredentialGrantAllowed);
    }

    @Override
    public boolean isRememberMe() {
        if (updated != null) return updated.isRememberMe();
        return cached.isRememberMe();
    }

    @Override
    public void setRememberMe(boolean rememberMe) {
        getDelegateForUpdate();
        updated.setRememberMe(rememberMe);
    }

    @Override
    public boolean isBruteForceProtected() {
        if (updated != null) return updated.isBruteForceProtected();
        return cached.isBruteForceProtected();
    }

    @Override
    public void setBruteForceProtected(boolean value) {
        getDelegateForUpdate();
        updated.setBruteForceProtected(value);
    }

    @Override
    public int getMaxFailureWaitSeconds() {
        if (updated != null) return updated.getMaxFailureWaitSeconds();
        return cached.getMaxFailureWaitSeconds();
    }

    @Override
    public void setMaxFailureWaitSeconds(int val) {
        getDelegateForUpdate();
        updated.setMaxFailureWaitSeconds(val);
    }

    @Override
    public int getWaitIncrementSeconds() {
        if (updated != null) return updated.getWaitIncrementSeconds();
        return cached.getWaitIncrementSeconds();
    }

    @Override
    public void setWaitIncrementSeconds(int val) {
        getDelegateForUpdate();
        updated.setWaitIncrementSeconds(val);
    }

    @Override
    public int getMinimumQuickLoginWaitSeconds() {
        if (updated != null) return updated.getMinimumQuickLoginWaitSeconds();
        return cached.getMinimumQuickLoginWaitSeconds();
    }

    @Override
    public void setMinimumQuickLoginWaitSeconds(int val) {
        getDelegateForUpdate();
        updated.setMinimumQuickLoginWaitSeconds(val);
    }

    @Override
    public long getQuickLoginCheckMilliSeconds() {
        if (updated != null) return updated.getQuickLoginCheckMilliSeconds();
        return cached.getQuickLoginCheckMilliSeconds();
    }

    @Override
    public void setQuickLoginCheckMilliSeconds(long val) {
        getDelegateForUpdate();
        updated.setQuickLoginCheckMilliSeconds(val);
    }

    @Override
    public int getMaxDeltaTimeSeconds() {
        if (updated != null) return updated.getMaxDeltaTimeSeconds();
        return cached.getMaxDeltaTimeSeconds();
    }

    @Override
    public void setMaxDeltaTimeSeconds(int val) {
        getDelegateForUpdate();
        updated.setMaxDeltaTimeSeconds(val);
    }

    @Override
    public int getFailureFactor() {
        if (updated != null) return updated.getFailureFactor();
        return cached.getFailureFactor();
    }

    @Override
    public void setFailureFactor(int failureFactor) {
        getDelegateForUpdate();
        updated.setFailureFactor(failureFactor);
    }

    @Override
    public boolean isVerifyEmail() {
        if (updated != null) return updated.isVerifyEmail();
        return cached.isVerifyEmail();
    }

    @Override
    public void setVerifyEmail(boolean verifyEmail) {
        getDelegateForUpdate();
        updated.setVerifyEmail(verifyEmail);
    }

    @Override
    public boolean isResetPasswordAllowed() {
        if (updated != null) return updated.isResetPasswordAllowed();
        return cached.isResetPasswordAllowed();
    }

    @Override
    public void setResetPasswordAllowed(boolean resetPasswordAllowed) {
        getDelegateForUpdate();
        updated.setResetPasswordAllowed(resetPasswordAllowed);
    }

    @Override
    public int getSsoSessionIdleTimeout() {
        if (updated != null) return updated.getSsoSessionIdleTimeout();
        return cached.getSsoSessionIdleTimeout();
    }

    @Override
    public void setSsoSessionIdleTimeout(int seconds) {
        getDelegateForUpdate();
        updated.setSsoSessionIdleTimeout(seconds);
    }

    @Override
    public int getSsoSessionMaxLifespan() {
        if (updated != null) return updated.getSsoSessionMaxLifespan();
        return cached.getSsoSessionMaxLifespan();
    }

    @Override
    public void setSsoSessionMaxLifespan(int seconds) {
        getDelegateForUpdate();
        updated.setSsoSessionMaxLifespan(seconds);
    }

    @Override
    public int getAccessTokenLifespan() {
        if (updated != null) return updated.getAccessTokenLifespan();
        return cached.getAccessTokenLifespan();
    }

    @Override
    public void setAccessTokenLifespan(int seconds) {
        getDelegateForUpdate();
        updated.setAccessTokenLifespan(seconds);
    }

    @Override
    public int getAccessCodeLifespan() {
        if (updated != null) return updated.getAccessCodeLifespan();
        return cached.getAccessCodeLifespan();
    }

    @Override
    public void setAccessCodeLifespan(int seconds) {
        getDelegateForUpdate();
        updated.setAccessCodeLifespan(seconds);
    }

    @Override
    public int getAccessCodeLifespanUserAction() {
        if (updated != null) return updated.getAccessCodeLifespanUserAction();
        return cached.getAccessCodeLifespanUserAction();
    }

    @Override
    public void setAccessCodeLifespanUserAction(int seconds) {
        getDelegateForUpdate();
        updated.setAccessCodeLifespanUserAction(seconds);
    }

    @Override
    public String getPublicKeyPem() {
        if (updated != null) return updated.getPublicKeyPem();
        return cached.getPublicKeyPem();
    }

    @Override
    public void setPublicKeyPem(String publicKeyPem) {
        getDelegateForUpdate();
        updated.setPublicKeyPem(publicKeyPem);
    }

    @Override
    public String getPrivateKeyPem() {
        if (updated != null) return updated.getPrivateKeyPem();
        return cached.getPrivateKeyPem();
    }

    @Override
    public void setPrivateKeyPem(String privateKeyPem) {
        getDelegateForUpdate();
        updated.setPrivateKeyPem(privateKeyPem);
    }

    @Override
    public PublicKey getPublicKey() {
        if (publicKey != null) return publicKey;
        publicKey = KeycloakModelUtils.getPublicKey(getPublicKeyPem());
        return publicKey;
    }

    @Override
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
        String publicKeyPem = KeycloakModelUtils.getPemFromKey(publicKey);
        setPublicKeyPem(publicKeyPem);
    }

    @Override
    public PrivateKey getPrivateKey() {
        if (privateKey != null) return privateKey;
        privateKey = KeycloakModelUtils.getPrivateKey(getPrivateKeyPem());
        return privateKey;
    }

    @Override
    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
        String privateKeyPem = KeycloakModelUtils.getPemFromKey(privateKey);
        setPrivateKeyPem(privateKeyPem);
    }

    @Override
    public List<RequiredCredentialModel> getRequiredCredentials() {

        List<RequiredCredentialModel> copy = new LinkedList<RequiredCredentialModel>();
        if (updated != null) copy.addAll(updated.getRequiredCredentials());
        else copy.addAll(cached.getRequiredCredentials());
        return copy;
    }

    @Override
    public void addRequiredCredential(String cred) {
        getDelegateForUpdate();
        updated.addRequiredCredential(cred);
    }

    @Override
    public PasswordPolicy getPasswordPolicy() {
        if (updated != null) return updated.getPasswordPolicy();
        return cached.getPasswordPolicy();
    }

    @Override
    public void setPasswordPolicy(PasswordPolicy policy) {
        getDelegateForUpdate();
        updated.setPasswordPolicy(policy);
    }

    @Override
    public boolean validatePassword(UserModel user, String password) {
        return CredentialValidation.validatePassword(this, user, password);
    }

    @Override
    public boolean validateTOTP(UserModel user, String password, String token) {
        if (!validatePassword(user, password)) return false;
        for (UserCredentialValueModel cred : user.getCredentialsDirectly()) {
            if (cred.getType().equals(UserCredentialModel.TOTP)) {
                return new TimeBasedOTP().validate(token, cred.getValue().getBytes());
            }
        }
        return false;
    }

    @Override
    public UserModel getUser(String name) {
        return cacheSession.getUserByUsername(name, this);
    }

    @Override
    public UserModel getUserByEmail(String email) {
        return cacheSession.getUserByEmail(email, this);
    }

    @Override
    public UserModel getUserById(String id) {
        return cacheSession.getUserById(id, this);
    }

    @Override
    public UserModel addUser(String id, String username) {
        getDelegateForUpdate();
        return updated.addUser(id, username);
    }

    @Override
    public UserModel addUser(String username) {
        getDelegateForUpdate();
        return updated.addUser(username);
    }

    @Override
    public boolean removeUser(String name) {
        getDelegateForUpdate();
        return updated.removeUser(name);
    }

    @Override
    public RoleModel getRoleById(String id) {
        if (updated != null) return updated.getRoleById(id);
        return cacheSession.getRoleById(id, this);
     }

    @Override
    public List<String> getDefaultRoles() {
        if (updated != null) return updated.getDefaultRoles();
        return cached.getDefaultRoles();
    }

    @Override
    public void addDefaultRole(String name) {
        getDelegateForUpdate();
        updated.addDefaultRole(name);
    }

    @Override
    public void updateDefaultRoles(String[] defaultRoles) {
        getDelegateForUpdate();
        updated.updateDefaultRoles(defaultRoles);
    }

    @Override
    public ClientModel findClient(String clientId) {
        if (updated != null) return updated.findClient(clientId);
        String appId = cached.getApplications().get(clientId);
        if (appId != null) {
            return cacheSession.getApplicationById(appId, this);
        }
        String oauth = cached.getClients().get(clientId);
        if (oauth != null) {
            return cacheSession.getOAuthClientById(oauth, this);
        }
        return null;
    }

    @Override
    public Map<String, ApplicationModel> getApplicationNameMap() {
        if (updated != null) return updated.getApplicationNameMap();
        Map<String, ApplicationModel> map = new HashMap<String, ApplicationModel>();
        for (String id : cached.getApplications().values()) {
            ApplicationModel model = cacheSession.getApplicationById(id, this);
            if (model == null) {
                throw new IllegalStateException("Cached application not found: " + id);
            }
            map.put(model.getName(), model);
        }
        return map;
    }

    @Override
    public List<ApplicationModel> getApplications() {
        if (updated != null) return updated.getApplications();
        List<ApplicationModel> apps = new LinkedList<ApplicationModel>();
        for (String id : cached.getApplications().values()) {
            ApplicationModel model = cacheSession.getApplicationById(id, this);
            if (model == null) {
                throw new IllegalStateException("Cached application not found: " + id);
            }
            apps.add(model);
        }
        return apps;

    }

    @Override
    public ApplicationModel addApplication(String name) {
        getDelegateForUpdate();
        ApplicationModel app = updated.addApplication(name);
        cacheSession.registerApplicationInvalidation(app.getId());
        return app;
    }

    @Override
    public ApplicationModel addApplication(String id, String name) {
        getDelegateForUpdate();
        ApplicationModel app =  updated.addApplication(id, name);
        cacheSession.registerApplicationInvalidation(app.getId());
        return app;
    }

    @Override
    public boolean removeApplication(String id) {
        cacheSession.registerApplicationInvalidation(id);
        getDelegateForUpdate();
        return updated.removeApplication(id);
    }

    @Override
    public ApplicationModel getApplicationById(String id) {
        if (updated != null) return updated.getApplicationById(id);
        return cacheSession.getApplicationById(id, this);
    }

    @Override
    public ApplicationModel getApplicationByName(String name) {
        if (updated != null) return updated.getApplicationByName(name);
        String id = cached.getApplications().get(name);
        if (id == null) return null;
        return getApplicationById(id);
    }

    @Override
    public void updateRequiredCredentials(Set<String> creds) {
        getDelegateForUpdate();
        updated.updateRequiredCredentials(creds);
    }

    @Override
    public UserModel getUserBySocialLink(SocialLinkModel socialLink) {
        if (updated != null) return updated.getUserBySocialLink(socialLink);
        return cacheSession.getUserBySocialLink(socialLink, this);
    }

    @Override
    public Set<SocialLinkModel> getSocialLinks(UserModel user) {
        if (updated != null) return updated.getSocialLinks(user);
        return cacheSession.getSocialLinks(user, this);
    }

    @Override
    public SocialLinkModel getSocialLink(UserModel user, String socialProvider) {
        if (updated != null) return updated.getSocialLink(user, socialProvider);
        return cacheSession.getSocialLink(user, socialProvider, this);
    }

    @Override
    public void addSocialLink(UserModel user, SocialLinkModel socialLink) {
        getDelegateForUpdate();
        updated.addSocialLink(user, socialLink);
    }

    @Override
    public boolean removeSocialLink(UserModel user, String socialProvider) {
        getDelegateForUpdate();
        return updated.removeSocialLink(user, socialProvider);
    }

    @Override
    public boolean isSocial() {
        if (updated != null) return updated.isSocial();
        return cached.isSocial();
    }

    @Override
    public void setSocial(boolean social) {
        getDelegateForUpdate();
        updated.setSocial(social);
    }

    @Override
    public boolean isUpdateProfileOnInitialSocialLogin() {
        if (updated != null) return updated.isUpdateProfileOnInitialSocialLogin();
        return cached.isUpdateProfileOnInitialSocialLogin();
    }

    @Override
    public void setUpdateProfileOnInitialSocialLogin(boolean updateProfileOnInitialSocialLogin) {
        getDelegateForUpdate();
        updated.setUpdateProfileOnInitialSocialLogin(updateProfileOnInitialSocialLogin);
    }

    @Override
    public List<UserModel> getUsers() {
        if (updated != null) return updated.getUsers();
        return cacheSession.getUsers(this);
    }

    @Override
    public List<UserModel> searchForUser(String search) {
        if (updated != null) return updated.searchForUser(search);
        return cacheSession.searchForUser(search, this);
    }

    @Override
    public List<UserModel> searchForUserByAttributes(Map<String, String> attributes) {
        if (updated != null) return updated.searchForUserByAttributes(attributes);
        return cacheSession.searchForUserByAttributes(attributes, this);
    }

    @Override
    public OAuthClientModel addOAuthClient(String name) {
        getDelegateForUpdate();
        OAuthClientModel client = updated.addOAuthClient(name);
        cacheSession.registerOAuthClientInvalidation(client.getId());
        return client;
    }

    @Override
    public OAuthClientModel addOAuthClient(String id, String name) {
        getDelegateForUpdate();
        OAuthClientModel client =  updated.addOAuthClient(id, name);
        cacheSession.registerOAuthClientInvalidation(client.getId());
        return client;
    }

    @Override
    public OAuthClientModel getOAuthClient(String name) {
        if (updated != null) return updated.getOAuthClient(name);
        String id = cached.getClients().get(name);
        if (id == null) return null;
        return getOAuthClientById(id);
    }

    @Override
    public OAuthClientModel getOAuthClientById(String id) {
        if (updated != null) return updated.getOAuthClientById(id);
        return cacheSession.getOAuthClientById(id, this);
    }

    @Override
    public boolean removeOAuthClient(String id) {
        cacheSession.registerOAuthClientInvalidation(id);
        getDelegateForUpdate();
        return updated.removeOAuthClient(id);
    }

    @Override
    public List<OAuthClientModel> getOAuthClients() {
        if (updated != null) return updated.getOAuthClients();
        List<OAuthClientModel> clients = new LinkedList<OAuthClientModel>();
        for (String id : cached.getClients().values()) {
            OAuthClientModel model = cacheSession.getOAuthClientById(id, this);
            if (model == null) {
                throw new IllegalStateException("Cached oauth client not found: " + id);
            }
            clients.add(model);
        }
        return clients;
    }

    @Override
    public Map<String, String> getSmtpConfig() {
        if (updated != null) return updated.getSmtpConfig();
        return cached.getSmtpConfig();
    }

    @Override
    public void setSmtpConfig(Map<String, String> smtpConfig) {
        getDelegateForUpdate();
        updated.setSmtpConfig(smtpConfig);
    }

    @Override
    public Map<String, String> getSocialConfig() {
        if (updated != null) return updated.getSocialConfig();
        return cached.getSocialConfig();
    }

    @Override
    public void setSocialConfig(Map<String, String> socialConfig) {
        getDelegateForUpdate();
        updated.setSocialConfig(socialConfig);
    }

    @Override
    public Map<String, String> getLdapServerConfig() {
        if (updated != null) return updated.getLdapServerConfig();
        return cached.getLdapServerConfig();
    }

    @Override
    public void setLdapServerConfig(Map<String, String> ldapServerConfig) {
        getDelegateForUpdate();
        updated.setLdapServerConfig(ldapServerConfig);
    }

    @Override
    public List<AuthenticationProviderModel> getAuthenticationProviders() {
        if (updated != null) return updated.getAuthenticationProviders();
        return cached.getAuthenticationProviders();
    }

    @Override
    public void setAuthenticationProviders(List<AuthenticationProviderModel> authenticationProviders) {
        getDelegateForUpdate();
        updated.setAuthenticationProviders(authenticationProviders);
    }

    @Override
    public String getLoginTheme() {
        if (updated != null) return updated.getLoginTheme();
        return cached.getLoginTheme();
    }

    @Override
    public void setLoginTheme(String name) {
        getDelegateForUpdate();
        updated.setLoginTheme(name);
    }

    @Override
    public String getAccountTheme() {
        if (updated != null) return updated.getAccountTheme();
        return cached.getAccountTheme();
    }

    @Override
    public void setAccountTheme(String name) {
        getDelegateForUpdate();
        updated.setAccountTheme(name);
    }

    @Override
    public String getAdminTheme() {
        if (updated != null) return updated.getAdminTheme();
        return cached.getAdminTheme();
    }

    @Override
    public void setAdminTheme(String name) {
        getDelegateForUpdate();
        updated.setAdminTheme(name);
    }

    @Override
    public String getEmailTheme() {
        if (updated != null) return updated.getEmailTheme();
        return cached.getEmailTheme();
    }

    @Override
    public void setEmailTheme(String name) {
        getDelegateForUpdate();
        updated.setEmailTheme(name);
    }

    @Override
    public int getNotBefore() {
        if (updated != null) return updated.getNotBefore();
        return cached.getNotBefore();
    }

    @Override
    public void setNotBefore(int notBefore) {
        getDelegateForUpdate();
        updated.setNotBefore(notBefore);
    }

    @Override
    public boolean removeRoleById(String id) {
        cacheSession.registerRoleInvalidation(id);
        getDelegateForUpdate();
        return updated.removeRoleById(id);
    }

    @Override
    public boolean isAuditEnabled() {
        if (updated != null) return updated.isAuditEnabled();
        return cached.isAuditEnabled();
    }

    @Override
    public void setAuditEnabled(boolean enabled) {
        getDelegateForUpdate();
        updated.setAuditEnabled(enabled);
    }

    @Override
    public long getAuditExpiration() {
        if (updated != null) return updated.getAuditExpiration();
        return cached.getAuditExpiration();
    }

    @Override
    public void setAuditExpiration(long expiration) {
        getDelegateForUpdate();
        updated.setAuditExpiration(expiration);
    }

    @Override
    public Set<String> getAuditListeners() {
        if (updated != null) return updated.getAuditListeners();
        return cached.getAuditListeners();
    }

    @Override
    public void setAuditListeners(Set<String> listeners) {
        getDelegateForUpdate();
        updated.setAuditListeners(listeners);
    }

    @Override
    public ApplicationModel getMasterAdminApp() {
        if (updated != null) return updated.getMasterAdminApp();
        return getApplicationById(cached.getMasterAdminApp());
    }

    @Override
    public void setMasterAdminApp(ApplicationModel app) {
        getDelegateForUpdate();
        updated.setMasterAdminApp(app);
    }

    @Override
    public RoleModel getRole(String name) {
        if (updated != null) return updated.getRole(name);
        String id = cached.getRealmRoles().get(name);
        if (id == null) return null;
        return cacheSession.getRoleById(id, this);
    }

    @Override
    public RoleModel addRole(String name) {
        getDelegateForUpdate();
        RoleModel role = updated.addRole(name);
        cacheSession.registerRoleInvalidation(role.getId());
        return role;
    }

    @Override
    public RoleModel addRole(String id, String name) {
        getDelegateForUpdate();
        RoleModel role =  updated.addRole(id, name);
        cacheSession.registerRoleInvalidation(role.getId());
        return role;
    }

    @Override
    public boolean removeRole(RoleModel role) {
        cacheSession.registerRoleInvalidation(role.getId());
        getDelegateForUpdate();
        return updated.removeRole(role);
    }

    @Override
    public Set<RoleModel> getRoles() {
        if (updated != null) return updated.getRoles();

        Set<RoleModel> roles = new HashSet<RoleModel>();
        for (String id : cached.getRealmRoles().values()) {
            RoleModel roleById = cacheSession.getRoleById(id, this);
            if (roleById == null) continue;
            roles.add(roleById);
        }
        return roles;
    }

    @Override
    public ClientModel findClientById(String id) {
        ClientModel model = getApplicationById(id);
        if (model != null) return model;
        return getOAuthClientById(id);
    }


    @Override
    public UsernameLoginFailureModel getUserLoginFailure(String username) {
        if (updated != null) return updated.getUserLoginFailure(username);
        return cacheSession.getUserLoginFailure(username, this);
    }

    @Override
    public UsernameLoginFailureModel addUserLoginFailure(String username) {
        if (updated != null) return updated.addUserLoginFailure(username);
        return cacheSession.addUserLoginFailure(username, this);
    }

    @Override
    public List<UsernameLoginFailureModel> getAllUserLoginFailures() {
        if (updated != null) return updated.getAllUserLoginFailures();
        return cacheSession.getAllUserLoginFailures(this);
    }

    @Override
    public UserSessionModel createUserSession(UserModel user, String ipAddress) {
        if (updated != null) return updated.createUserSession(user, ipAddress);
        return cacheSession.createUserSession(this, user, ipAddress);
    }

    @Override
    public UserSessionModel getUserSession(String id) {
        if (updated != null) return updated.getUserSession(id);
        return cacheSession.getUserSession(id, this);
    }

    @Override
    public List<UserSessionModel> getUserSessions(UserModel user) {
        if (updated != null) return updated.getUserSessions(user);
        return cacheSession.getUserSessions(user, this);
    }

    @Override
    public void removeUserSession(UserSessionModel session) {
        if (updated != null) {
            updated.removeUserSession(session);
        } else {
            cacheSession.removeUserSession(session);

        }
    }

    @Override
    public void removeUserSessions(UserModel user) {
        if (updated != null) {
            updated.removeUserSessions(user);
        } else {
            cacheSession.removeUserSessions(this, user);

        }
    }

    @Override
    public void removeExpiredUserSessions() {
        if (updated != null) {
            updated.removeExpiredUserSessions();
        } else {
            cacheSession.removeExpiredUserSessions(this);

        }
    }

    @Override
    public void removeUserSessions() {
        if (updated != null) {
            updated.removeUserSessions();
        } else {
            cacheSession.removeUserSessions(this);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof RealmModel)) return false;

        RealmModel that = (RealmModel) o;
        return that.getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
