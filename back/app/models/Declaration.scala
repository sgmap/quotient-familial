package models

case class Declaration(
                        declarant1: Declarant,
                        declarant2: Declarant,
                        dateRecouvrement: String,
                        dateEtablissement: String,
                        nombreParts: Float,
                        situationFamille: String,
                        nombrePersonnesCharge: Int,
                        revenuBrutGlobal: Int,
                        revenuImposable: Int,
                        impotRevenuNetAvantCorrections: Option[Int],
                        montantImpot: Option[Int],
                        revenuFiscalReference: Int,
                        anneeImpots: String,
                        anneeRevenus: String
                        )

case class Declarant(
                      nom: String,
                      nomNaissance: String,
                      prenoms: String,
                      dateNaissance: String
                      )


object JsonDeclarationFormats {

  import play.api.libs.json.Json


  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val declarantFormat = Json.format[Declarant]
  implicit val declarationFormat = Json.format[Declaration]

}
