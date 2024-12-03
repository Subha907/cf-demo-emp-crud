
# setup:

## local Dev setup

Uses H2 database in dev through application.properties (for local dev and testing)

## Cloudfoundry Environment set up for dev and prod

Uses H2 db (the default one) if dev is activated

Uses external database in prod through environment variables if prod is activated


# Manifest file to deploy the app to cf env
Manifest file used in this project has two different configurations dev and prod

## dev
If we want to push the app with dev configuration then use the following command line [specify the app name of dev configuartion]:
cf push -f manifest.yml employee-management-dev

## prod
If we want to push the app with prod configuration then use the following command line [specify the app name of prod configuartion]:
cf push -f manifest.yml employee-management-prod

After pushing the app with prod configuration then set the Environment Variables used in the manifest file

cf set-env employee-management-prod SPRING_DATASOURCE_URL "jdbc:mysql://mysql-host.example.com:3306/<db_name>?ssl-mode=REQUIRED"
cf set-env employee-management-prod SPRING_DATASOURCE_DRIVER_CLASS_NAME "com.mysql.cj.jdbc.Driver"
cf set-env employee-management-prod SPRING_DATASOURCE_USERNAME "<prod_user>"
cf set-env employee-management-prod SPRING_DATASOURCE_PASSWORD "<prod_password>"
cf set-env employee-management-prod SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT "org.hibernate.dialect.MySQL8Dialect"
cf set-env employee-management-prod SPRING_JPA_HIBERNATE_DDL_AUTO "update"

Restage the app after setting the env variables
cf restage employee-management-prod










