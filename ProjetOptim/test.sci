//Programmation lineaire: Probleme 2
c=[-5.0;-2.0;-3.0;-2.0];
b=[50.0;22.0];
A=[1.0,2.0,0.0,5.0;11.0,12.0,10.0,12.0];
Zu=[];
Z1=[0;0;0;0];
[Zopt,lag,CA]=linpro(c,A,b,Z1,Zu);