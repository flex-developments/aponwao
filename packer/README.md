# Empaquetado con Debreate

**El proceso de empaquetado de aponwao con Debreate de forma manual resulta tedioso por lo que se recomienda el uso de métodos más directos con otras soluciones.**

Ahora bien, para poder entender el proceso de empaquetado es necesario entender la forma como se estructura el directorio de la aplicación y su despliegue. El directorio aponwao se compone de la siguiente forma:

 * /Uninstaller: Almacena los archivos que ejecutan el proceso de desinstalación.
 * /aponwao-preferencias: Almacena los archivos de configuración por default.
 * /bin: Almacena los scripts de invocación de aponwao para disparar el proceso de java que ejecuta la aplicación.
 * /lib: Almacena todas las librerías necesarias para la ejecución de la aplicación.
 * /resources: Almacena los recursos que consume aponwao para la construcción de su interfaz gráfica (imágenes, archivo de strings internacionalizados, etc)

En relación al empaquetado posterior a la modificación del código fuente, resulta especialmente importante /lib ya que en ella se encuentra el corazón de la aplicación. La afirmación anterior resulta del modo como funciona el llamado inicial de aponwao.

A pesar de estar programado en java, la invocación de aponwao inicia con la ejecución de un scrip shell de la carpeta /bin, el script se encargará de setear las variables de entorno necesarias y finalmente iniciará un proceso de java que invoca a la librería de aponwao necesario. Es decir que al ejecutar aponwao para escritorio el script termina llamando a /lib/aponwaodesktop.jar y el jar se encarga de las relaciones con los recursos y librerías restantes, así como la ejecución del resto de las operaciones.

Entonces, al realizar alguna modificación de aponwao es necesario actualizar el archivo correspondiente en /packer/aponwao y reempaquetar esta carpeta.

## Ejemplos
Ejemplo en el que se requiere modificar una de las imágenes usadas por aponwao:
 1. Modificar o sustituir la imagen deseada en /packer/aponwao/resources/images
 2. Seguir los pasos para empaquetamiento de la carpeta /packer/aponwao

Ejemplo en el que se requiere modificar uno de los mensajes que genera aponwao:
 1. Modificar el archivo /packer/aponwao/resources/i18n/language_es_ES.properties
 2. Seguir los pasos para empaquetamiento de la carpeta /packer/aponwao

Ejemplo en el que se requiere modificar una linea de código en la librería aponwaodesktop:
 1. Modificar línea de código en el archivo .java necesario
 2. Compilar librería para generar el aponwaodesktop.jar
 3. Reemplazar aponwaodesktop.jar en /packer/aponwao/lib/aponwaodesktop.jar
 4. Seguir los pasos para empaquetamiento de la carpeta /packer/aponwao

El proceso de empaquetamiento se lleva a cabo una véz han sido sustituídos los archivos que hayan sido modificados dentro de https://github.com/suscerte/aponwao/tree/java/master/packer/aponwao

El proceso de empaquetamiento con debreate se lleva a cabo suministrando la información que solicita la aplicación en sus diversos formularios.

###### Formulario 1
Package: aponwao
Version: 1.3
Maintainer: suscerte.gob.ve
Mail: soportedra@suscerte.gob.ve
Architecture: i386
Priority: optional
Short Description: Firma Electrónica
Description: Aplicación para la firma electrónica de documentos PDF basada en sinadura.

###### Formulario 2
Depends: rsync (>=3)
Recommends: oracle-java7-installer (>=7)
#Recommends: openjdk-7-jre (>=7) | oracle-java7-installer (>=7)

###### Formulario 3
Target: /usr/local/aponwao

Seleccionar el directorio https://github.com/suscerte/aponwao/tree/java/master/packer/aponwao y agregar a la lista de archivos.

###### Formulario 4
----------------------- Ficha PostInstall ---------------------------------
```shell
#!/bin/bash
clear
echo "Inicializando archivos en el home........................."
UHOME="/home"
USERS=$(cut -d':' -f1 /etc/passwd)
for u in $USERS
do
	ID=$(id -u $u)
	if [ $ID -ge 1000 ] ; then
		if [ -d ${UHOME}/${u} ] ; then
			#MOVER ARCHIVOS
                       cp -r /usr/local/aponwao/aponwao-preferencias /${UHOME}/${u}/aponwao-preferencias
                       sed -i 's/<usuario>/'${u}'/g' /home/${u}/aponwao-preferencias/*.properties
                       chmod -R 755 /${UHOME}/${u}/aponwao-preferencias
                       chown -R ${u}:${u} /${UHOME}/${u}/aponwao-preferencias
		       #CREAR CARPETAS
		       mkdir -p /${UHOME}/${u}/Firmados
		       mkdir -p /${UHOME}/${u}/Sin_Firmar
                       mkdir -p /${UHOME}/${u}/Respaldo
		       chown -R ${u}:${u} /${UHOME}/${u}/Firmados
		       chown -R ${u}:${u} /${UHOME}/${u}/Sin_Firmar
		       chown -R ${u}:${u} /${UHOME}/${u}/Respaldo
		fi
	fi
done
rm -r /usr/local/aponwao/aponwao-preferencias
echo "Inicializando archivo de configuracion........................."
cp /usr/local/aponwao/resources/images/aplicationLogo.png /usr/share/pixmaps/aplicationLogo.png
chmod -R 755 /usr/share/pixmaps/aplicationLogo.png
cp /usr/local/aponwao/bin/aponwao /usr/local/bin/aponwao
chmod -R 755 /usr/local/bin/aponwao
chmod -R 755 /usr/local/aponwao
echo ""
echo "******************************************************"
echo "                      ATENCION"
echo " Aponwao requiere la instalación de la plataforma"
echo " Java-1.7 para su ejecución."
echo ""
echo " De momento se ha omitido la obligatoriedad de las"
echo " dependencias supra mencionadas para la construcción"
echo " de este paquete .deb, con la finalidad de permitir"
echo " al usuario la instalación manual de dicha plataforma"
echo " sin registrarla en los paquetes del sistema."
echo "******************************************************"
echo ""
echo "Listo!"
```
------------------------ Ficha PostRemove ---------------------------------
```shell
#!/bin/bash
echo "Eliminando enlaces"
#BORRAR CARPETA DE PROPIEDADES
UHOME="/home"
USERS=$(cut -d':' -f1 /etc/passwd)
for u in $USERS
do
	ID=$(id -u $u)
	if [ $ID -ge 1000 ] ; then
		if [ -d ${UHOME}/${u} ] ; then
			rm -r ${UHOME}/${u}/aponwao-preferencias
		fi
	fi
done
echo "Listo!"
```

###### Formulario 5
Name=aponwao
Version=1.3
suscerte.gob.ve
soportedra@suscerte.gob.ve

###### Formulario 6
```
Copyright (C) 2008 - zylk.net
 * zylk.net (http://www.zylk.net/)

Desarrollo apoyado por la Superintendencia de Servicios de Certificación Electrónica (SUSCERTE) durante 2010 por:
* Ing. Felix D. Lopez M. - flex.developments en gmail.com | flopez en suscerte.gob.ve
* Ing. Yessica De Ascencao - yessicadeascencao en gmail.com | ydeascencao en suscerte.gob.ve

Adaptaciones realizadas por:
 * Ing. Felix D. Lopez M. - flex.developments en gmail
 * Ing. Yessica De Ascencao - yessicadeascencao en gmail.com

This file is part of Sinadura.

Sinadura is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

Sinadura is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Sinadura.  If not, see <http://www.gnu.org/licenses/>.

See COPYRIGHT.txt for copyright notices and details.
```

###### Formulario 7
Name=Aponwao
Type=Application
Exec=aponwao
Terminal=false
Comment=Firma Electrónica
StartupNotify=false
Icon=/usr/share/pixmaps/aplicationLogo.png
Encoding=UTF-8
Categories=Office;
