#!/usr/bin/env python2
# -*- coding: utf-8 -*-

# Importaciones de Stdlib

# Importaciones del core de PyQt
from PyQt4.QtGui import (QMainWindow, QIcon, QAction, QToolBar, qApp)
from PyQt4.QtCore import Qt, SIGNAL

# Importaciones de aplicaciones de terceras partes

# Importaciones de la aplicación propia
from settings import base as config
from ui.qt.preferences import PreferenciasDialog


class MainWindow(QMainWindow):
    '''
    Ventana principal
    '''

    def __init__(self):
        super(MainWindow, self).__init__()
        self.setWindowTitle(self.tr(config.APP_NAME)) # Titulo
        self.setMinimumSize(config.ANCHO, config.ALTO) # Tamaño mínimo
        self.setWindowIcon(QIcon(config.LOGO_APP))

        # Menubar
        menu = self.menuBar() # Crear barra de menú

        self.__crear_acciones()
        self.__crear_menu(menu)

        # Toolbar
        self.toolbar = QToolBar()
        self.__crear_toolbar(self.toolbar)
        self.addToolBar(Qt.RightToolBarArea, # Alinear el toolbar a la derecha
                self.toolbar)

    def __crear_acciones(self):
        self.preferencias = QAction(self.tr("Preferencias"), self)
        self.preferencias.triggered.connect(self.mostrar_preferencias)

        self.salir = QAction(self.tr("Salir"), self)
        self.salir.setShortcut("Ctrl+Q")
        self.salir.triggered.connect(qApp.quit)

        self.acerca = QAction(self.tr("Acerca de"), self)

    def __crear_menu(self, menu_bar):
        '''
        Función creadora de las opciones:
            Archivo
            Ayuda
        '''

        # Archivo
        menu_archivo = menu_bar.addMenu(self.tr("&Archivo"))
        menu_archivo.addAction(self.preferencias)
        menu_archivo.addAction(self.salir)

        # Ayuda
        menu_ayuda = menu_bar.addMenu(self.tr("A&yuda"))
        menu_ayuda.addAction(self.acerca)

        # ToolBar
        self.agregar_documento = QAction(self.tr("Agregar documento"), self)
        self.agregar_directorio = QAction(self.tr("Agregar directorio"), self)
        self.eliminar = QAction(self.tr("Eliminar"), self)
        self.visualizar = QAction(self.tr("Visualizar"), self)
        self.enviar = QAction(self.tr("Enviar por correo"), self)
        self.firmar = QAction(self.tr("Firmar"), self)
        self.firmar_duplicados = QAction(self.tr("Firmar duplicados"), self)
        self.validar = QAction(self.tr("Validar"), self)

    def __crear_toolbar(self, toolbar):
        toolbar.addAction(self.agregar_documento)
        toolbar.addAction(self.agregar_directorio)
        toolbar.addAction(self.eliminar)
        toolbar.addAction(self.visualizar)
        toolbar.addAction(self.enviar)
        toolbar.addAction(self.firmar)
        toolbar.addAction(self.firmar_duplicados)
        toolbar.addAction(self.validar)

    def mostrar_preferencias(self):
        preferencias = PreferenciasDialog()
        print "hola"
        preferencias.show()
