#!/usr/bin/env python2
# -*- coding: utf-8 -*-

import os

# Configuraciones globales de la APP
APP_NAME = 'Aponwao'
ANCHO = 750
ALTO = 500

# PROJECT_ROOT es el directorio raíz de nuestro proyecto
PROJECT_ROOT = os.path.join(os.getcwd())

# STATIC_ROOT define el directorio donde se almacenaran los
# archivos estáticos: imágenes, estilos, etc
STATIC_ROOT = os.path.join(os.getcwd(), 'static/')

# STATIC_IMAGES define la ubicación de las imágenes
STATIC_IMAGES = os.path.join(STATIC_ROOT, 'images/')

# STATIC_QSS define los estilos de PyQt
STATIC_QSS = os.path.join(STATIC_ROOT, 'qss/')

# LOGO_APP es el archivo principal para identificar el logo de la app
LOGO_APP = STATIC_IMAGES + 'aponwao.svg'
