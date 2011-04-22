<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
   
  <html>
      <link rel="stylesheet" media="screen" type="text/css" title="Design" href="design.css" />
      <title>Résultats de vos problèmes</title>
      <script type="text/javascript" src="script.js"></script>
  <body>

      <div id="blocTitre">
            <h1>Maël Razavet: voici vos problèmes</h1>
      </div>

    <div id="blocProbleme">
         <xsl:for-each select="listeProblemes/probleme">
            <li><a href="#" onclick="afficheProbleme()"><xsl:value-of select="titre"/></a></li>
         </xsl:for-each>
    </div>

    <div class="blocResultats">
        <xsl:variable name='var'>Mon probleme</xsl:variable>
         <xsl:for-each select="listeProblemes/probleme">
            <xsl:if test="titre=$var">
                <xsl:value-of select="description"/><br/>
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
                 <br/>
                 <!-- affichage des contraintes-->
                 <!--
                    S'occuper du cas ou le coeff=0 et faire attention au +
                 -->
                 <xsl:for-each select="contrainte">
                     <xsl:for-each select="variable">
                         <xsl:choose>
                            <xsl:when test="@coeff=1">
                                <xsl:text>x</xsl:text><xsl:number value="position()" format="1"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="@coeff"/><xsl:text>x</xsl:text><xsl:number value="position()" format="1"/>
                            </xsl:otherwise>
                         </xsl:choose>
                        <xsl:if test="position()!=last()">
                          <xsl:text>+</xsl:text>
                        </xsl:if>
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
            </xsl:if>
         </xsl:for-each>
<!--<xsl:value-of select="$var"/>-->

    </div>

  </body>
  </html>
  </xsl:template>

</xsl:stylesheet>
