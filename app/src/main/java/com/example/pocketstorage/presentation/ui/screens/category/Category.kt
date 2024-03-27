package com.example.pocketstorage.presentation.ui.screens.category

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pocketstorage.R
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.presentation.ui.screens.category.viewmodel.CategoryViewModel

@Composable
fun Category(viewModel: CategoryViewModel) {
    CategoryScreen(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryScreen(viewModel: CategoryViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val categoriesState by viewModel.categoriesState.collectAsState()

    var errorText by rememberSaveable {
        mutableStateOf("")
    }

    var errorTextIsVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.onSearchTextChange("")
    }

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
                                color = colorResource(id = R.color.SpanishGrey)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "SearchById"
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.RetroBlue),
                            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                        ),
                        categoriesState!!.searchText,
                        viewModel::onSearchTextChange
                    )
                }

            }

            Row(
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {


                ButtonForTheCategoryScreen(
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
                        Log.d("categoryScreen", "$this")
                    }
                )

            }
            RenderScreen(viewModel = viewModel, uiState = uiState)
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
                    .height(400.dp),
                onDismissRequest = {
                    showBottomSheet = false
                    errorTextIsVisible = false
                },
                sheetState = sheetState
            ) {

                var categoryName: String? by rememberSaveable {
                    mutableStateOf(null)
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Sheet content
                    Text("New category", fontSize = 20.sp)
                    TextFieldSearchCategoryWithoutIcon(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 24.dp),
                        label = { Text(text = "name") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.RetroBlue),
                            unfocusedBorderColor = colorResource(id = R.color.SpanishGrey),
                        ),
                        onTextFieldValueChange = { categoryNameFromTextField ->
                            categoryName = categoryNameFromTextField
                        }
                    )

                    if (errorTextIsVisible) {
                        Text(
                            modifier = Modifier.height(20.dp),
                            text = errorText,
                            fontSize = 16.sp,
                            color = colorResource(
                                id = R.color.error
                            )
                        )
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    ButtonForTheCategoryScreen(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 64.dp),
                        rowContent = {
                            Column {
                                Text(text = "Save", fontSize = 16.sp)
                            }
                        },
                        onClick = {

                            if (!categoryName.isNullOrEmpty() && categoryName!!.isNotBlank()) {
                                if (!categoriesState?.existingCategoriesForCurrentLocation?.any { category -> category.name == categoryName }!!) {
                                    if (!categoriesState?.currentLocationId.isNullOrEmpty()) {
                                        val category = Category(
                                            name = categoryName!!,
                                            buildingId = categoriesState!!.currentLocationId
                                        )

                                        viewModel.saveCategoryOnLocalStorage(category){
                                            viewModel.getAllCategoriesByLocationId(categoriesState.currentLocationId)
                                        }


                                        Toast.makeText(
                                            context,
                                            "Category $categoryName was created",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        showBottomSheet = false
                                        errorTextIsVisible = false

                                    } else {
                                        errorText = "*You have to determine the location"
                                        errorTextIsVisible = true
                                    }

                                } else {
                                    errorText = "*This category already exists"
                                    errorTextIsVisible = true
                                }

                            } else {
                                errorText = "*Category field name is empty"
                                errorTextIsVisible = true
                            }

                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                                errorTextIsVisible = false
                            }

                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RenderScreen(viewModel: CategoryViewModel, uiState: CategoriesUiState) {
    val categoriesState by viewModel.categoriesState.collectAsState()
    when (uiState) {
        is CategoriesUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is CategoriesUiState.Success -> {
            LaunchedEffect(categoriesState!!.currentLocationId) {
                viewModel.getAllCategoriesByLocationId(categoriesState!!.currentLocationId)
            }

            if (uiState.isEmpty()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "No locations", modifier = Modifier.align(Alignment.Center))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp)
                        .background(Color.White)
                ) {
                    items(uiState.categories) { categories ->
                        ProductsOfTheCategories(categories)
                    }
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
    colors: TextFieldColors,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        leadingIcon = leadingIcon,
        colors = colors
    )
}

@Composable
fun TextFieldSearchCategoryWithoutIcon(
    onTextFieldValueChange: (String) -> Unit,
    modifier: Modifier,
    label: @Composable () -> Unit,
    colors: TextFieldColors
) {
    var categoryNameFromTextField by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = modifier,
        value = categoryNameFromTextField,
        onValueChange = {
            categoryNameFromTextField = it
            onTextFieldValueChange.invoke(categoryNameFromTextField)
        },
        label = label,
        colors = colors
    )
}

@Composable
fun ButtonForTheCategoryScreen(
    modifier: Modifier,
    rowContent: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.RetroBlue)),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            rowContent()
        }
    }
}


data class ItemsCategoryModel(val nameProduct: String)

@Composable
fun ExpandableListItem(
    category: Category,
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
                .background(colorResource(id = R.color.AdamantineBlue))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "image",
                tint = Color.White
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "5 elements",
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

@Composable
fun ProductsOfTheCategories(category: Category) {
    val selectedItems = remember { mutableStateListOf<String>() }

    Column {
        ExpandableListItem(
            category = category,
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


