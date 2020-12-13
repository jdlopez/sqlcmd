# sqlcmd
SQL command line executor using JDBC drivers. Useful for cron jobs and some automation. Porting of my previous sf.net code

Usage:

    java -jar sqlcmd.jar -c:sqlcmd_config.properties <<EOF
        SELECT *
        FROM MY_TABLE
    EOF

Config values:

* jdbcDriverPath=Path to jdbc driver jar (loaded dynamically)
* jdbcDriverClass=Jdbc Driver class full name
* jdbcUrl=Jdbc connection URL
* jdbcUser=
* jdbcPass=
* inputSQL=Path to input sql file (not required/optional if not present it uses stdin)
* outputResult=Path to output file (if not present uses stdout)
* printHeader=true/false If true adds header with query's column name. Default true
* printFieldSeparator=Field separator for printing. Default tab (\t)

Config values can be set also with parameters:

    -p:paran_name=param_value

Ex:

    -p:printHeader=false

