<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">

        <html>
            <!--<link rel="stylesheet" media="screen" type="text/css" title="Design" href="design.css" /><script type="text/javascript" src="script.js"></script>-->
            <head>
                <title>Résultats de vos problèmes</title>
                <style type="text/css">
                body {
                    min-width:640px;
                    width: 1024px;
                    background-color: #fffff;
                     margin-left: auto;
                    margin-right: auto;
                }
                #blocTitre, #blocProbleme, .blocResultats {
                    display:inline-block;
                    margin-bottom: 10px;
                    margin-right: 2px;
                }

                #blocTitre{
                    background-image:url("BaniereFinal.png");
                    background-repeat:no-repeat;
                    height: 150px;
                    text-align: center;
                    width:1024px;
                    -moz-border-radius:2px;
                    -webkit-border-radius:2px;
                    -moz-box-shadow: 6px 6px 5px #90A4A1;
                    -webkit-box-shadow: 6px 6px 5px #90A4A1;
                    -o-box-shadow: 6px 6px 5px #90A4A1;
                    box-shadow: 6px 6px 5px #90A4A1;
                }

                #conteneur{
                    width: 1024px;;
                    float: left;
                    margin-left: auto;
                    margin-right: auto;
                }

                #blocProbleme{
                    float: left;
                    width: 200px;
                    margin-left: 0px;
                    position: absolute;
                    margin-top: 130px;
                    min-height: 370px;
                    height: 450px;
                    -moz-border-radius:5px;
                    -webkit-border-radius:5px;
                    -moz-box-shadow: 6px 6px 5px #90A4A1;
                    -webkit-box-shadow: 6px 6px 5px #90A4A1;
                    -o-box-shadow: 6px 6px 5px #90A4A1;
                    box-shadow: 6px 6px 5px #90A4A1;
                    background:-moz-linear-gradient(top, #59ffff 0%, #1988a0 100%);
                }

                .blocResultats{
                    float: right;
                    width: 805px;
                    margin-right: -0px;
                    min-height: 580px;
                    -moz-border-radius:5px;
                    -webkit-border-radius:5px;
                    -moz-box-shadow: 6px 6px 5px #90A4A1;
                    -webkit-box-shadow: 6px 6px 5px #90A4A1;
                    -o-box-shadow: 6px 6px 5px #90A4A1;
                    box-shadow: 6px 6px 5px #90A4A1;
                    background:-moz-linear-gradient(top, #59ffff 0%, #1988a0 100%);
                }


                legend{
                    font-weight: bold;
                    font-size: 20px;
                    text-decoration: underline;
                    font-family: "Arial Black", Arial, Verdana, serif;
                }

                fieldset{
                    color:#4C69C7;
                    border: 2px solid #4C69C7;
                    font-size: 20px;
                    font-weight: bold;
                    text-align: center;
                }

                #fctObecjtive{
                    font-size: 16px;
                    text-align: center;
                }

                #contraintes{
                    text-align: center;
                }

                #spanRes{
                    font-weight: bold;
                    font-size: 20px;
                    text-decoration: underline;
                    font-family: "Arial Black", Arial, Verdana, serif;
                }

                #contraintesSpan{
                    font-weight: bold;
                    font-size: 20px;
                    text-decoration: underline;
                    font-family: "Arial Black", Arial, Verdana, serif;
                }

                #utilisateur{
                    margin-bottom: 5px;
                    -moz-border-radius:5px 5px 0px 0px;
                    -webkit-border-radius:5px 5px 0px 0px;
                    text-align: center;
                    font-size: 20px;
                    color: black;
                    background:-moz-linear-gradient(top, #194da0 0%, #59ffff 100%);
                }

                #profil{
                    margin-bottom: 5px;
                    padding-left: 5px;
                }

                #conteneurProfil{
                    float: left;
                    height: 120px;
                    width: 200px;
                    -moz-border-radius:5px;
                    -webkit-border-radius:5px;
                     background:-moz-linear-gradient(top, #59ffff 0%, #1988a0 100%);
                    -moz-box-shadow: 6px 6px 5px #90A4A1;
                    -webkit-box-shadow: 6px 6px 5px #90A4A1;
                    -o-box-shadow: 6px 6px 5px #90A4A1;
                    box-shadow: 6px 6px 5px #90A4A1;
                }

                #titreProbleme{
                    margin-bottom: 5px;
                    -moz-border-radius:5px 5px 0px 0px;
                    -webkit-border-radius:5px 5px 0px 0px;
                    padding-left: 5px;
                    text-align: center;
                    font-size: 20px;
                    color: black;
                    background:-moz-linear-gradient(top, #194da0 0%, #59ffff 100%);
                }

                #listeProblemes{
                    padding-left: 20px;
                }

                #titreBlocResultats{
                     background:-moz-linear-gradient(top, #194da0 0%, #59ffff 100%);
                    -moz-border-radius:5px 5px 0px 0px;
                    -webkit-border-radius:5px 5px 0px 0px;
                    text-align: center;
                    font-size: 20px;
                }

                #resultats{
                    text-align: center;
                }

                #span_profil{
                    padding-left: 20px;
                    font-size: 20px;
                    padding-top: 15px;
                    position: absolute;
                }
                </style>
            </head>
            <body onload="init()">
                <!-- <script language="text/javascript">
                    //fonction qui initialise la page html
function init(){
    titreBlocProbleme=document.getElementById("titreBlocResultats");
    titreBlocProbleme.style.display="none";
    //on récupère tous les elements de classname=lien
    elementsHref=document.getElementsByClassName('lien');
    //on recup tous les elements bloc de probleme res
    elements=document.getElementsByClassName('res');
    //on parcours les tableaux des elements
    for(var i=0; i<elements.length; i++){
        //on cache tous les blocs
        elements[i].style.display="none";
        //on modifie tous les id de deux tableaux en leur attribuant un numero
        elements[i].setAttribute("id", i);
        elementsHref[i].setAttribute("id", i);
    }
}

//fonction qui affiche le bloc dont le lien correspond
function afficheProbleme(obj){
    element=document.getElementById("image");
    titreBlocProbleme=document.getElementById("titreBlocResultats");
    titreBlocProbleme.style.display="block";
    //on recupere l'id
    numero=obj.getAttribute("id");
    //on recupere tous les blocs res
    elements=document.getElementsByClassName('res');
     //on parcours tous les elements du tableau
    for(var i=0; i<elements.length; i++){
        //si lelement correspond au probleme cliqué
        if(elements[i].getAttribute("id")==numero){
            //on affiche son bloc
            elements[i].style.display="block";
            element.style.display="none";
        }
        //sinon
        else{
            //on laisse cacher les autres blocs
            elements[i].style.display="none";
        }
    }
}



                </script>-->

                <script type="text/javascript">

     

    </script>

                <div id="blocTitre">
                </div>

                <div id="conteneur">
                    <!-- affichage du profil-->
                    <div id="conteneurProfil">
                        <div id="utilisateur">
                        Utilisateur
                        </div>
                        <div id="profil">
                            <img src="../../../../../{listeProblemes/@url}"/>
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
                        
                        <xsl:for-each select="listeProblemes/probleme">
                            <div class="res" id="">
                                <div id="titreBlocResultats">
                                    <xsl:value-of select="titre"/>
                                </div>
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
                                <div id="fctObecjtive">
                                    <span id="contraintesSpan">Fonction objective: </span>
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
                                <div id="contraintes">
                                    <span id="contraintesSpan">avec les contraintes:</span>
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
                                <div id="resultats">
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

    
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
