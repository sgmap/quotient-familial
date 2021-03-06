package services

import javax.inject.Inject

import models.Declaration
import models.JsonDeclarationFormats._
import play.Play.application
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsValue
import play.api.libs.ws._

import scala.concurrent.Future

class ApiParticulierService(ws: WSClient, baseUrl: String, apiKey: String) {

  @Inject def this(ws: WSClient) =
    this(
      ws,
      application.configuration.getString("apiparticulier.host"),
      application.configuration.getString("apiparticulier.key"))


  def declaration(numeroFiscal: String, referenceAvis: String): Future[Option[Declaration]] = {
    svairRequest(numeroFiscal, referenceAvis).get().map {
      response =>
        response.status match {
          case 404 => None
          case 200 => response.json.validate[Declaration].asOpt
        }
    }
  }

  private def svairRequest(numeroFiscal: String, referenceAvis: String): WSRequest = ws
    .url(baseUrl + "/api/impots/svair")
    .withHeaders("Accept" -> "application/json")
    .withHeaders("X-API-Key" -> apiKey)
    .withQueryString("numeroFiscal" -> numeroFiscal)
    .withQueryString("referenceAvis" -> referenceAvis)

  def adress(numeroFiscal: String, referenceAvis: String): Future[Option[JsValue]] = {
    adressRequest(numeroFiscal, referenceAvis).get().map {
      response =>
        response.status match {
          case 404 => None
          case 200 => Some(response.json)
        }
    }
  }

  private def adressRequest(numeroFiscal: String, referenceAvis: String): WSRequest = ws
    .url(baseUrl + "/api/impots/adress")
    .withHeaders("Accept" -> "application/json")
    .withHeaders("X-API-Key" -> apiKey)
    .withQueryString("numeroFiscal" -> numeroFiscal)
    .withQueryString("referenceAvis" -> referenceAvis)
}
