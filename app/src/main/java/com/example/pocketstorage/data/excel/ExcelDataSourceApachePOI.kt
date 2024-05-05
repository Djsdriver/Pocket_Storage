package com.example.pocketstorage.data.excel

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import com.example.pocketstorage.domain.model.TableInventory
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ExcelDataSourceApachePOI(private val context: Context) :
    ExcelDataSource {
    override fun exportInventoryToExcelFile(tableInventoryList: List<TableInventory>) {

        val workbook = HSSFWorkbook()

        val sheet = workbook.createSheet("Excel Export")

        val header = sheet.createRow(0)

        var headerColumn = 0

        tableInventoryList[0].properties.forEach {
            val cell = header.createCell(headerColumn)
            cell.setCellValue(it.toString().split(" ")[1])
            headerColumn++
        }

        var row = 1

        tableInventoryList.forEach { tableInventory ->

            val currentRow = sheet.createRow(row)

            var column = 0

            tableInventory.properties.forEach {
                val cell = currentRow.createCell(column)
                cell.setCellValue(it.get())
                column++
            }

            row++
        }

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)

        val externalDir = context.getExternalFilesDir("Excel files")
        val newFile = File(externalDir, "Inventories ${formattedDateTime}.xlsx")
        val fileOut = FileOutputStream(newFile)
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
    }
}

