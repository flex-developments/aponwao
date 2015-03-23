#!/usr/bin/env python2
# -*- coding: utf-8 -*-

from PyQt4.QtGui import QApplication

from ui.qt.main_window import MainWindow

if __name__ == '__main__':
    import sys

    app = QApplication(sys.argv)

    main = MainWindow()
    main.show()

    sys.exit(app.exec_())
