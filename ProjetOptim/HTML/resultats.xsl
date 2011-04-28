<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">

  <html>
      <link rel="stylesheet" media="screen" type="text/css" title="Design" href="design.css" />
      <title>Résultats de vos problèmes</title>
      <script type="text/javascript" src="script.js"></script>
  <body onload="init()">

      <div id="blocTitre">
            <h1><xsl:value-of select="listeProblemes/@utilisateur"/>: voici vos problèmes</h1>
      </div>

    <div id="blocProbleme">
         <xsl:for-each select="listeProblemes/probleme">
            <li><a href="#" id="" class="lien" onclick="afficheProbleme(this)"><xsl:value-of select="titre"/></a></li>
         </xsl:for-each>
    </div>

    <div class="blocResultats">
         <xsl:for-each select="listeProblemes/probleme">
             <div class="res" id="">
                <!-- affichage de la description-->
                <fieldset>
                <legend id="description">Description:</legend>
                <xsl:value-of select="description"/><br/>
                </fieldset><br/>
                <!-- affichage de la fonction objective-->
                <xsl:for-each select="objectif">
                    <xsl:choose>
                <xsl:when test="@type='Minimiser'">
                <B><xsl:text>min F = </xsl:text></B>
                </xsl:when>
                <xsl:otherwise>
                <B><xsl:text>max F = </xsl:text></B>
                </xsl:otherwise>
                </xsl:choose>
                </xsl:for-each>
                 <xsl:for-each select="objectif/variable">
                    <B><xsl:value-of select="@coeff"/><xsl:text>x</xsl:text><xsl:number value="position()" format="1"/></B>
                    <xsl:if test="position()!=last()">
                     <B><xsl:text>+</xsl:text></B>
                    </xsl:if>
                 </xsl:for-each>
                 <br/><br/>
                 <!-- affichage des contraintes-->
                 <!--
                    S'occuper du cas ou le coeff=0 et faire attention au +
                 -->
                 <xsl:for-each select="contrainte">
                     <xsl:for-each select="variable">
                         <xsl:choose>
                            <xsl:when test="position() != 1">

                            <xsl:if test="@coeff=1">
                                <xsl:text>+</xsl:text><xsl:text>x</xsl:text><xsl:number value="position()" format="1"/>
                            </xsl:if>
                            <xsl:if test="@coeff=0">

                            </xsl:if>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>x</xsl:text><xsl:number value="position()" format="1"/>
                            </xsl:otherwise>
                         </xsl:choose>
                          <!--      <xsl:value-of select="@coeff"/><xsl:text>x</xsl:text><xsl:number value="position()" format="1"/>
                                <xsl:if test="position()!=last()">
                                    <xsl:text>+</xsl:text>
                                </xsl:if>
                            </xsl:otherwise>
                         </xsl:choose>
                         <xsl:if test="./@coeff!=./following-sibling::variable[position()=1]/@coeff">

                         </xsl:if>
                         <xsl:choose>
                        <xsl:when test="@coeff=0">

                        </xsl:when>
                        <xsl:when test="position()!=last()">
                          <xsl:text>+</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:text>+</xsl:text>
                        </xsl:otherwise>
                        </xsl:choose>-->
                     </xsl:for-each>
                     <xsl:choose>
                     <xsl:when test="@type='Infériorité'">
                        <span>&#60;&#61;</span>
                     </xsl:when>
                     <xsl:when test="@type='Supériorité'">
                        <span>&#62;&#61;</span>
                     </xsl:when>
                     <xsl:otherwise>
                        <span>&#61;</span>
                     </xsl:otherwise>
                     </xsl:choose>
                     <xsl:value-of select="constante/@valeur"/><br/>
                 </xsl:for-each>
            </div>
         </xsl:for-each>
    </div>

  </body>
  </html>
  </xsl:template>

</xsl:stylesheet>
