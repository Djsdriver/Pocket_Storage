package com.example.pocketstorage.data.excel

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.pocketstorage.core.utils.UNDEFINED_ID
import com.example.pocketstorage.domain.model.TableInventory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ExcelDataSourceApachePOI(private val context: Context) :
    ExcelDataSource {
    override fun exportInventoryToExcelFile(tableInventoryList: List<TableInventory>) {

        val workbook = HSSFWorkbook()

        val sheet = workbook.createSheet("Inventories")

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "Inventories $formattedDateTime.xls")
            }

            val excelUri = context
                .contentResolver
                .insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            if (excelUri != null) {
                val outputStream = context.contentResolver.openOutputStream(excelUri)

                try {
                    workbook.write(outputStream)
                } finally {
                    outputStream?.close()
                }
            }
        } else {

            val externalDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            if (externalDir.canWrite()) {
                val newFile = File(externalDir, "Inventories ${formattedDateTime}.xls")
                val fileOut = FileOutputStream(newFile)

                try {
                    workbook.write(fileOut)
                } finally {
                    fileOut.close()
                }
            }
        }

        workbook.close()
    }

    override suspend fun importInventoryFromExcelFile(uriFile: String?) : List<TableInventory> {

        val inventoryList = mutableListOf<TableInventory>()

        withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(uriFile!!.toUri())
            if (inputStream != null) {
                val workbook = HSSFWorkbook(inputStream)
                val sheet = workbook.getSheetAt(0)


                for (i in 1 until sheet.physicalNumberOfRows) {
                    val row = sheet.getRow(i)

                    val name = row.getCell(1)?.stringCellValue ?: ""
                    val description = row.getCell(2)?.stringCellValue ?: ""
                    val categoryName = row.getCell(3)?.stringCellValue ?: ""
                    val locationName = row.getCell(4)?.stringCellValue ?: ""
                    val locationIndex = row.getCell(5)?.stringCellValue ?: ""
                    val locationAddress = row.getCell(6)?.stringCellValue ?: ""

                    val inventory = TableInventory(
                        name = name,
                        description = description,
                        categoryName = categoryName,
                        locationName = locationName,
                        locationIndex = locationIndex,
                        locationAddress = locationAddress
                    )
                    inventoryList.add(inventory)
                }

                workbook.close()
                inputStream.close()
            } else {
                // Обработка ошибки чтения inputStream
            }
        }


        return inventoryList

    }
}
