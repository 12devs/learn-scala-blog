package com.cgbc.dashboard.utils.json

import org.json4s.{DefaultFormats, Formats}

trait AllSerializers {
  implicit val formats: Formats = DefaultFormats ++ IdSerializers.all ++ TypeSerializers.all ++ JavaTimeSerializers.all
}
