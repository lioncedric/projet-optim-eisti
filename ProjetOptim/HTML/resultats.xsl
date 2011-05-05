<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">

        <html>
            <link rel="stylesheet" media="screen" type="text/css" title="Design" href="design.css" />
            <title>Résultats de vos problèmes</title>
            <script type="text/javascript" src="script.js"></script>
            <body onload="init()">

                <div id="blocTitre">
                    <h1>
                        <xsl:value-of select="listeProblemes/@utilisateur"/>: voici vos problèmes
                    </h1>
                </div>

                <div id="conteneur">
                    <!-- affichage du profil-->
                    <div id="conteneurProfil">
                    <div id="utilisateur">
                        Utilisateur
                    </div>
                    <div id="profil">
                        <img src="../{listeProblemes/@url}"/>
                        <span id="span_profil"><xsl:value-of select="listeProblemes/@utilisateur"/></span>
                    </div>
                    </div>
                    <!--affichage des problemes sous forme de menu-->
                    <div id="blocProbleme">
                        <div id="titreProbleme">
                            Problème
                        </div>
                        <div id="listeProblemes">
                        <xsl:for-each select="listeProblemes/probleme">
                            <li>
                                <a href="#" id="" class="lien" onclick="afficheProbleme(this)">
                                    <xsl:value-of select="titre"/>
                                </a>
                            </li>
                        </xsl:for-each>
                        </div>
                    </div>


                    <!-- affichage du résultat cliqué et des résultats -->
                    <div class="blocResultats">
                        <div id="image">
            Bienvenue sur le site OptimEisti
                        </div>
                        <div id="titreBlocResultats">
                            <xsl:value-of select="listeProblemes/probleme/titre"/>
                        </div>
                        <xsl:for-each select="listeProblemes/probleme">
                            <div class="res" id="">
                                
                <!-- affichage de la description-->
                                <div id="description">
                                    <fieldset>
                                        <legend id="description">Description:</legend>
                                        <xsl:value-of select="description"/>
                                        <br/>
                                    </fieldset>
                                </div>
                                <br/>
                <!-- affichage de la fonction objective-->
                                <div id="fctObecjtive"><span id="contraintesSpan">Fonction objective: </span><xsl:text>    </xsl:text>
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
                                            <xsl:value-of select="@coeff"/>
                                            <xsl:text>x</xsl:text>
                                            <xsl:number value="position()" format="1"/>
                                        </B>
                                        <xsl:if test="position()!=last()">
                                            <B>
                                                <xsl:text> + </xsl:text>
                                            </B>
                                        </xsl:if>
                                    </xsl:for-each>
                                </div>
                                <br/>
                                <br/>
                 <!-- affichage des contraintes-->
                                <div id="contraintes">
                                    <span id="contraintesSpan">avec les contraintes:</span><br/>
                                    
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
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <xsl:text>x</xsl:text>
                                                    <xsl:number value="position()" format="1"/>
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
                                </div><br/><br/>
                 <!-- Affichage des résultats-->
                                <div id="resultats">
                                    <span id="spanRes">Résultats:</span><br/>
                                        <xsl:for-each select="resultat">
                                            F = <xsl:value-of select="@valeur"/><br/>
                                                <xsl:for-each select="variable">
                                                    <xsl:text>x</xsl:text>
                                                    <xsl:number value="position()" format="1"/><xsl:text>=</xsl:text>
                                                    <xsl:value-of select="@coeff"/>
                                                    <br/>
                                             </xsl:for-each>
                                        </xsl:for-each>
                                </div>
                            </div>
                        </xsl:for-each>
                    </div>
                </div>

    
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
