//fonction qui initialise la page html
function init(){
    //on récupère le div du titre du bloc de résultats
    titreBlocProbleme=document.getElementsByClassName("titreBlocResultats");
    //on le cache
    titreBlocProbleme[0].style.display="none";
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
    //On récupère le div de l'image d'accueil
    element=document.getElementById("image");
    //on enlève l'image
    element.style.display="none";
    //on récupère le div du titre du bloc de résultats
    titreBlocProbleme=document.getElementsByClassName("titreBlocResultats");
    //on l'affiche
    titreBlocProbleme[0].style.display="block";
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
        }
        //sinon
        else{
            //on laisse cacher les autres blocs
            elements[i].style.display="none";
        }
    }
}