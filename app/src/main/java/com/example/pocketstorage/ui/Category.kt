package com.example.pocketstorage.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocketstorage.R
import kotlinx.coroutines.launch

@Composable
fun Category() {
    CategoryScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryScreen() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .padding(top = 56.dp, start = 24.dp, bottom = 24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {


                Box(Modifier.weight(1f)) {
                    TextFieldSearchCategory(
                        modifier = Modifier
                            .padding(end = 24.dp)
                            .fillMaxWidth(),
                        label = {
                            Text(
                                text = "category",
                                color = colorResource(id = R.color.gray)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "SearchById"
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.blue),
                            unfocusedBorderColor = colorResource(id = R.color.gray),
                        )
                    )
                }

            }

            Row(
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {


                ButtonAddCategory(
                    modifier = Modifier.wrapContentWidth(),
                    rowContent = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add category",
                            modifier = Modifier.padding(end = 15.dp)
                        )
                        Text(text = "Add category", fontSize = 16.sp)
                    },
                    onClick = {
                        showBottomSheet = true
                    }
                )

            }

            //recycler
            val list = mutableListOf<CategoryModel>()
            list.add(CategoryModel("Printer", R.drawable.ic_launcher_foreground, "Products: 4"))
            list.add(CategoryModel("Monitor", R.drawable.ic_launcher_foreground, "Products: 4"))
            list.add(CategoryModel("Printer", R.drawable.ic_launcher_foreground, "Products: 4"))
            list.add(CategoryModel("Monitor", R.drawable.ic_launcher_foreground, "Products: 4"))
            list.add(CategoryModel("Printer", R.drawable.ic_launcher_foreground, "Products: 4"))
            list.add(CategoryModel("Monitor", R.drawable.ic_launcher_foreground, "Products: 4"))
            list.add(CategoryModel("Printer", R.drawable.ic_launcher_foreground, "Products: 4"))
            list.add(CategoryModel("Monitor", R.drawable.ic_launcher_foreground, "Products: 4"))

            LazyColumn(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .background(Color.White)
            ) {
                items(list) { 
                    MyScreen()
                }
            }

        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .height(400.dp),
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Sheet content
                    Text("New category", fontSize = 20.sp)
                    TextFieldSearchCategory(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 24.dp, bottom = 38.dp),
                        label = { Text(text = "name") },
                        leadingIcon = { /*TODO*/ },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.blue),
                            unfocusedBorderColor = colorResource(id = R.color.gray)
                        )
                    )
                    ButtonAddCategory(
                        modifier = Modifier.wrapContentWidth(),
                        rowContent = { Text(text = "Save", fontSize = 16.sp) },
                        onClick = {
                            // Click
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}





@Composable
fun TextFieldSearchCategory(
    modifier: Modifier,
    label: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    colors: TextFieldColors
) {
    var textIdInventory by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        value = textIdInventory,
        onValueChange = { textIdInventory = it },
        label = label,
        leadingIcon = leadingIcon,
        colors = colors
    )
}


@Composable
fun ButtonAddCategory(
    modifier: Modifier,
    rowContent: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            rowContent()
        }
    }
}


data class CategoryModel(val name: String, val image: Int, val countProduct: String)
data class ItemsCategoryModel(val nameProduct: String)

@Composable
fun ExpandableListItem(
    model: CategoryModel,
    items: List<ItemsCategoryModel>,
    onItemClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Заголовок элемента списка
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.blue_light))
        ) {
            Icon(
                painter = painterResource(id = model.image),
                contentDescription = "image",
                tint = Color.White
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = model.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = model.countProduct,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = if (expanded) R.drawable.expand_more else R.drawable.expand_more_less),
                contentDescription = "Expand/Collapse",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
        }


        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }
        // Список элементов, отображаемых при развернутом состоянии
        if (expanded) {
            Column(
                modifier = Modifier
                    .size(width = 300.dp, height = 150.dp)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(state)
            ) {
                items.forEach { item ->
                    Text(
                        text = item.nameProduct,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .clickable { onItemClick(item.nameProduct) }
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreen() {
    val selectedItems = remember { mutableStateListOf<String>() }
    val list = mutableListOf<CategoryModel>()
    list.add(CategoryModel("Printer", R.drawable.ic_launcher_foreground, "Products: 4"))

    Column {
        // Список расширяемых элементов
        list.forEach { item ->
            ExpandableListItem(
                model = item,
                items = listOf(
                    ItemsCategoryModel("Philiphs 241V8L"),
                    ItemsCategoryModel("Philiphs 241V8L"),
                    ItemsCategoryModel("Philiphs 241V8L"),
                    ItemsCategoryModel("Philiphs 241V8L"),
                    ItemsCategoryModel("Philiphs 241V8L"),
                    ItemsCategoryModel("Philiphs 241V8L"),
                    ItemsCategoryModel("Philiphs 241V8L"),
                    ItemsCategoryModel("Philiphs 241V8L"),
                ),
                onItemClick = { subitem ->
                    if (selectedItems.contains(subitem)) {
                        selectedItems.remove(subitem)
                    } else {
                        selectedItems.add(subitem)
                    }
                }
            )
        }

    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottom() {
    var showBottomSheet by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = { showBottomSheet = true },
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = "New category")
        TextFieldSearchCategory(
            modifier = Modifier.wrapContentWidth(),
            label = { /*TODO*/ },
            leadingIcon = { /*TODO*/ },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.blue),
                unfocusedBorderColor = colorResource(id = R.color.gray),
            )
        )

    }

}
