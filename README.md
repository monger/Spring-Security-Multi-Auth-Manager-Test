Spring Security Multi-Authentication-Manager Test
=================================================

_A little background..._

I was involved with two separate projects that both required the rare feature of providing two entirely distinct
authentication providers for a single web application.

One authentication provider handled authenticating admin users against an LDAP server.  The admin users would log into
the web application through a standard, secured HTML login page.

The other authentication provider handled authenticating web service consumers.  In one project the consumers consumed
Hessian services while the other project provided REST services for consumption.  In both projects, the application
itself held the authentication information, stored in the database.

With both projects, the admin HTML interface would only ever be available to admins and never to the consumers of the
application's services.  Likewise, the service consumers would never have reason to access the admin interface of the
application.  With this in mind, _we didn't want to chain Spring authentication providers in a single Spring
authentication manager_ but rather required two entirely separate and distinct authentication managers.

With the release of Spring Security 3.1 and the introduction of the `authentication-manager-ref` `<http>` attribute, it
appeared that I would be able to easily implement the sovereign, separate authentication managers described above.

The [Spring Security 3.1 documentation](http://static.springsource.org/spring-security/site/docs/3.1.x/reference/springsecurity-single.html)
describes the `authentication-manager-ref` element as the following:

> A reference to the AuthenticationManager used for the FilterChain created by this http element.

This description led me to believe that I would be able to create an authentication manager exclusively for one `<http>`
element and another authentication manager for another `<http>` manager.  This feature fit my design requirements
perfectly.

However, in practice it appeared that when defining multiple authentication managers, Spring Security would *always*
only use the last authentication manager defined.  It would ignore all authentication managers that were defined prior
to the final one.  This project demonstrates that behavior.

How to reproduce
----------------

I have created a set of integration tests that attempt to access an HTML page of the admin interface and also access
Hessian services.  These two activities require two separate authentication managers for authentication.

I am performing the authentication over a non-encrypted connection so that you won't have to perform any keystore
self-signed certificate configuration.

Open `src/main/webapp/WEB-INF/spring/spring-security.xml`

At the bottom of `spring-security.xml` you will see two authentication managers defined at the bottom of the file.  One
is `remotingAuthenticationManager` and the other is `adminAuthenticationManager`.

If you ensure that the `remotingAuthenticationManager` is placed _after_ `adminAuthenticationManager`, all of the
remoting integration tests will pass while the `loginWithLoserUserTest` and `loginWithSuperUserTest` tests will fail.
Then, if you modify `spring-security.xml` so that the `adminAuthenticationManager` comes after the
`remotingAuthenticationManager`, all of the admin interface integration tests will pass while the `testHessianService`
test fails.

After you make your changes to `src/main/webapp/WEB-INF/spring/spring-security.xml`, run the following:

    mvn integration-test

This will run the integration tests and generate the Failsafe reports.

After the integration tests are run, you can view the results by opening the following file in your browser:

    target/failsafe-reports/index.html
