#!/usr/bin/env python2
# -*- coding: utf-8 -*-

from PyQt4.QtGui import QDialog


class PreferenciasDialog(QDialog):

    def __init__(self, parent=None):
        QDialog.__init__(self, parent)

        self.setWindowTitle("Preferencias")
