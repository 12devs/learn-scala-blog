package com.cgbc.dashboard

case class AppConfig(db: DatabaseModule, http: HttpConfig)
case class DatabaseModule(default: DatabaseConfig)
case class DatabaseConfig(driver: String, url: String, user: String, password: String)
case class HttpConfig(host: String, port: Int)