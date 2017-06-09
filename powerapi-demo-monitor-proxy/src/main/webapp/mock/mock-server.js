/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict'

import sinon from 'sinon'

const HTTP_STATUS_OK = 200
const MEDIA_TYPE_JSON = {'Content-Type': 'application/json'}

const PROCESSING_TIME = Object.freeze({
  min: 10000,
  max: 11500
})

const POWER = Object.freeze({
  min: 5,
  max: 8,
  maxStep: 0.3
})

const randomFloat = (min, max) => Math.random() * (max - min) + min
const randomInt = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min

const server = sinon.fakeServer.create()
server.autoRespond = true

server.respondWith('GET', /fetch-cyrils.*/, (xhr) => {
  const procTime = randomInt(PROCESSING_TIME.min, PROCESSING_TIME.max)
  const measureNb = procTime / 100
  const power = []

  let last = randomFloat(POWER.min, POWER.max)
  for (let i = 0; i < measureNb; ++i) {
    power.push(last)
    last = randomFloat(Math.max(last - POWER.maxStep, POWER.min), Math.min(last + POWER.maxStep, POWER.max))
  }

  xhr.respond(HTTP_STATUS_OK, MEDIA_TYPE_JSON, JSON.stringify({
    proxied: ['Cyril Chopelet', 'Cyril McKree'],
    power,
    processingTime: procTime
  }))
})

export default server
