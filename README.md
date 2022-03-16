# sqlcmd
SQL command line executor using JDBC drivers. Useful for cron jobs and some automation.

_Porting of my previous sf.net code_

Usage:

    java -jar sqlcmd.jar -c:sqlcmd_config.properties <<EOF
        SELECT *
        FROM MY_TABLE
    EOF

If _mailSendTo_ configured the tool sends an email with formatted output.

## Config values:

* jdbcDriverPath=Path to jdbc driver jar (loaded dynamically) if null it must be in classpath
* jdbcDriverClass=Jdbc Driver class full name (no need if driver registers itself)
* jdbcUrl=Jdbc connection URL
* jdbcUser=
* jdbcPass=
* inputSQL=Path to input sql file (not required/optional if not present it uses stdin)
* outputResult=Path to output file (if not present uses stdout)

## Printing and formatting values:
* printHeader=true/false If true adds header with query's column name. Default true
* printFieldSeparator=Field separator for printing. Default tab (\t)
* formatterName=Formatter type, possible values: text, html or csv (default text)

## Mail config values:

Mail Server:
* mailHost=SMTP host
* mailPort=SMTP port
* mailAuth=true/false smtp server needs authentication?
* mailTLS=true/false smtp server needs TLS connection?
* mailUser=smtp auth username
* mailPass=smtp auth password
* 
Mail message:
* mailFrom=Email addess in from field
* mailSubject=Subject field
* mailSendTo=Email addess in TO field

## Config notes

Config values can be set also with parameters:

    -p:paran_name=param_value

Ex:

    -p:printHeader=false

