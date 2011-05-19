<?xml version="1.0" encoding="utf-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
       <xsl:output
        method="html"
        encoding="utf-8"
        omit-xml-declaration="no"
        doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN"
        indent="yes"/>
        <html>
            
            <head>
                <link rel="stylesheet" media="screen" type="text/css" title="Design" href="./html/design.css" />
                <script type="text/javascript" src="./html/script.js"></script>
                <title>Résultats de vos problèmes</title>
            </head>
            <body onload="init()">
                <div id="blocTitre">
                </div>

                <div id="conteneur">
                    <!-- affichage du profil-->
                    <div id="conteneurProfil">
                        <div id="utilisateur">
                        Utilisateur
                        </div>
                        <div id="profil">

                            <img src="file:///{listeProblemes/@url}" id="imageProfil" alt="Image introuvable"/>
                            <span id="span_profil">
                                <xsl:value-of select="listeProblemes/@utilisateur"/>
                            </span>
                        </div>
                    </div>
                    <!--affichage des problemes sous forme de menu-->
                    <div id="blocProbleme">
                        <div id="titreProbleme">
                            Problème
                        </div>
                        <div id="listeProblemes">
                            <ul>
                            <xsl:for-each select="listeProblemes/probleme">
                                <li>
                                    <a href="#" class="lien" onclick="afficheProbleme(this)">
                                        <xsl:value-of select="titre"/>
                                    </a>
                                </li>
                            </xsl:for-each>
                            </ul>
                        </div>
                    </div>


                    <!-- affichage du résultat cliqué et des résultats -->
                    <div class="blocResultats">
                        <div id="image">
                          <img src="./html/pageBienvenue.png" id="imageBienvenue" alt="Bienvenue sur le site OptimEisti"/>
                        </div>
                        
                        <xsl:for-each select="listeProblemes/probleme">
                            <div class="res">
                                <div class="titreBlocResultats">
                                    <xsl:value-of select="titre"/>
                                </div>
                <!-- affichage de la description-->
                                <div>
                                    <fieldset>
                                        <legend>Description:</legend>
                                        <xsl:value-of select="description"/>
                                        <br/>
                                    </fieldset>
                                </div>
                                <br/>
                <!-- affichage de la fonction objective-->
                                <div class="fctObecjtive">
                                    <span class="contraintesSpan">Fonction objective: </span><br/>
                                    <xsl:text>    </xsl:text>
                                    <xsl:for-each select="objectif">
                                        <xsl:choose>
                                            <xsl:when test="@type='Minimiser'">
                                                <B>
                                                    <xsl:text>min F = </xsl:text>
                                                </B>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <B>
                                                    <xsl:text>max F = </xsl:text>
                                                </B>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:for-each>
                                    <xsl:for-each select="objectif/variable">
                                        <B>
                                            <xsl:choose>
                                                <xsl:when test="position() != 1">
                                                    <xsl:if test="@coeff=1">
                                                        <xsl:text> + </xsl:text>
                                                        <xsl:text>x</xsl:text>
                                                        <xsl:number value="position()" format="1"/>
                                                    </xsl:if>
                                                    <xsl:if test="@coeff=0">

                                                    </xsl:if>
                                                    <xsl:if test="@coeff!=1">
                                                        <xsl:if test="substring(@coeff,1,1)='-'">
                                                            <xsl:if test="@coeff=-1">
                                                                <xsl:text> - </xsl:text>
                                                                <xsl:text>x</xsl:text>
                                                                <xsl:number value="position()" format="1"/>
                                                            </xsl:if>
                                                            <xsl:if test="@coeff!=-1">
                                                                <xsl:text> - </xsl:text>
                                                                <xsl:value-of select="substring(@coeff,2,string-length(@coeff))"/>
                                                                <xsl:text>x</xsl:text>
                                                                <xsl:number value="position()" format="1"/>
                                                            </xsl:if>
                                                        </xsl:if>
                                                        <xsl:if test="substring(@coeff,1,1)!='-'">
                                                            <xsl:text> + </xsl:text>
                                                            <xsl:value-of select="@coeff"/>
                                                            <xsl:text>x</xsl:text>
                                                            <xsl:number value="position()" format="1"/>
                                                        </xsl:if>
                                                    </xsl:if>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <xsl:if test="@coeff=1">
                                                        <xsl:text>x</xsl:text>
                                                        <xsl:number value="position()" format="1"/>
                                                    </xsl:if>
                                                    <xsl:if test="@coeff=0">

                                                    </xsl:if>
                                                    <xsl:if test="@coeff!=1">
                                                        <xsl:if test="@coeff=-1">
                                                            <xsl:text> - </xsl:text>
                                                            <xsl:text>x</xsl:text>
                                                            <xsl:number value="position()" format="1"/>
                                                        </xsl:if>
                                                        <xsl:if test="@coeff!=-1">
                                                            <xsl:value-of select="substring(@coeff,1,string-length(@coeff))"/>
                                                            <xsl:text>x</xsl:text>
                                                            <xsl:number value="position()" format="1"/>
                                                        </xsl:if>
                                                    </xsl:if>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </B>
                                    </xsl:for-each>
                                </div>
                                <br/>
                                <br/>
                 <!-- affichage des contraintes-->
                                <div class="contraintes">
                                    <span class="contraintesSpan">avec les contraintes:</span>
                                    <br/>
                                    <xsl:for-each select="contrainte">
                                        <xsl:for-each select="variable">
                                            <xsl:choose>
                                                <xsl:when test="position() != 1">
                                                    <xsl:if test="@coeff=1">
                                                        <xsl:text> + </xsl:text>
                                                        <xsl:text>x</xsl:text>
                                                        <xsl:number value="position()" format="1"/>
                                                    </xsl:if>
                                                    <xsl:if test="@coeff=0">
                                                        
                                                    </xsl:if>
                                                    <xsl:if test="@coeff!=1">
                                                        <xsl:if test="substring(@coeff,1,1)='-'">
                                                            <xsl:if test="@coeff=-1">
                                                                <xsl:text> - </xsl:text>
                                                                <xsl:text>x</xsl:text>
                                                                <xsl:number value="position()" format="1"/>
                                                            </xsl:if>
                                                            <xsl:if test="@coeff!=-1">
                                                                <xsl:text> - </xsl:text>
                                                                <xsl:value-of select="substring(@coeff,2,string-length(@coeff))"/>
                                                                <xsl:text>x</xsl:text>
                                                                <xsl:number value="position()" format="1"/>
                                                            </xsl:if>
                                                        </xsl:if>
                                                        <xsl:if test="substring(@coeff,1,1)!='-'">
                                                            <xsl:text> + </xsl:text>
                                                            <xsl:value-of select="@coeff"/>
                                                            <xsl:text>x</xsl:text>
                                                            <xsl:number value="position()" format="1"/>
                                                        </xsl:if>
                                                    </xsl:if>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <xsl:if test="@coeff=1">
                                                        <xsl:text>x</xsl:text>
                                                        <xsl:number value="position()" format="1"/>
                                                    </xsl:if>
                                                    <xsl:if test="@coeff=0">

                                                    </xsl:if>
                                                    <xsl:if test="@coeff!=1">
                                                        <xsl:if test="@coeff=-1">
                                                            <xsl:text> - </xsl:text>
                                                            <xsl:text>x</xsl:text>
                                                            <xsl:number value="position()" format="1"/>
                                                        </xsl:if>
                                                        <xsl:if test="@coeff!=-1">
                                                            <xsl:value-of select="substring(@coeff,1,string-length(@coeff))"/>
                                                            <xsl:text>x</xsl:text>
                                                            <xsl:number value="position()" format="1"/>
                                                        </xsl:if>
                                                    </xsl:if>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </xsl:for-each>
                                        <xsl:choose>
                                            <xsl:when test="@type='Infériorité'">
                                                <span> &#60;&#61; </span>
                                            </xsl:when>
                                            <xsl:when test="@type='Supériorité'">
                                                <span> &#62;&#61; </span>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <span> &#61; </span>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                        <xsl:value-of select="constante/@valeur"/>
                                        <br/>
                                    </xsl:for-each>
                                    <xsl:for-each select="objectif">
                                        <xsl:for-each select="variable">
                                            <xsl:text>x</xsl:text>
                                            <xsl:number value="position()" format="1"/>
                                            <span> &#62;&#61; </span>0
                                            <br/>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </div>
                                <br/>
                                <br/>
                                <!-- Affichage des résultats-->
                                <div class="resultats">
                                    <xsl:for-each select="resultat">
                                        <span id="spanRes">Résultats:</span>
                                        <br/>
                                            F = 
                                        <xsl:value-of select="@valeur"/>
                                        <br/>
                                        <xsl:for-each select="variable">
                                            <xsl:text>x</xsl:text>
                                            <xsl:number value="position()" format="1"/>
                                            <xsl:text>=</xsl:text>
                                            <xsl:value-of select="@coeff"/>
                                            <br/>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </div>
                            </div>
                        </xsl:for-each>
                    </div>
                </div>

                 <p>
    <a href="http://validator.w3.org/check?uri=referer"><img
        src="http://www.w3.org/Icons/valid-html401"
        alt="Valid HTML 4.01 Transitional" height="31" width="88"/></a>
  </p>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
