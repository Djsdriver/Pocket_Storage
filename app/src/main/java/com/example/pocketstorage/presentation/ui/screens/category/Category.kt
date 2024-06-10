package com.example.pocketstorage.presentation.ui.screens.category

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocketstorage.R
import com.example.pocketstorage.components.DialogWithImage
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.presentation.ui.screens.category.viewmodel.CategoryViewModel
import com.example.pocketstorage.utils.SnackbarManager
import com.example.pocketstorage.utils.SnackbarMessage
import com.example.pocketstorage.utils.SnackbarMessage.Companion.toMessage

@Composable
fun Category(viewModel: CategoryViewModel, onClickExpandedItem: (String) -> Unit) {
    CategoryScreen(viewModel) {
        onClickExpandedItem(it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    onClickExpandedItem: (String) -> Unit
) {
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
    val snackbarMessage by SnackbarManager.snackbarMessages.collectAsState()

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
                        categoriesState.searchText,
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
            RenderScreen(viewModel = viewModel, uiState = uiState) {
                onClickExpandedItem(it)
                Log.d("ExpandItemsId", "$it")
            }


        }
        SnackBarToast(
            snackbarMessage = snackbarMessage,
            context = context
        )

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
                                if (!categoriesState.existingCategoriesForCurrentLocation.any { category -> category.name == categoryName }!!) {
                                    if (!categoriesState.currentLocationId.isNullOrEmpty()) {
                                        val category = Category(
                                            name = categoryName!!,
                                            buildingId = categoriesState.currentLocationId
                                        )

                                        viewModel.saveCategoryOnLocalStorage(category) {
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
private fun RenderScreen(
    viewModel: CategoryViewModel,
    uiState: CategoriesUiState,
    onClickExpandedItem: (String) -> Unit
) {
    val categoriesState by viewModel.categoriesState.collectAsState()
    when (uiState) {
        is CategoriesUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is CategoriesUiState.Success -> {
            LaunchedEffect(
                categoriesState.currentLocationId,
                categoriesState.existingCategoriesForCurrentLocation
            ) {
                viewModel.getAllCategoriesByLocationId(categoriesState.currentLocationId)
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
                    items(uiState.categories,
                        key = {
                            it.id
                        }) { categories ->
                        ProductsOfTheCategories(
                            items = categoriesState.inventoryList,
                            category = categories,
                            viewModel = viewModel,
                            onClickExpandedItem = {
                                onClickExpandedItem(it)
                            }
                        )
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


@Composable
fun ExpandableListItem(
    category: Category,
    items: List<Inventory?>,
    onItemClick: (String) -> Unit,
    onItemCategoryClick: (String) -> Unit,
    viewModel: CategoryViewModel
) {

    val expandedCategoryId by viewModel.categoriesState.collectAsState()
    val isLongPressActive = remember { mutableStateOf(false) }
    val itemCountInCategory = expandedCategoryId.allListInventory.filter { it?.categoryId == category.id }.size


    Column {
        // Заголовок элемента списка
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(UInt) {
                    detectTapGestures(
                        onTap = {
                            if (expandedCategoryId.activeCategory == category.id) {
                                viewModel.clearActiveCategory() // Clear active category when clicked again
                            } else {
                                onItemCategoryClick(category.id) // Set clicked category as active
                            }
                        },
                        onLongPress = {
                            isLongPressActive.value = true
                        }
                    )
                }
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
                    text = itemCountInCategory.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = if (expandedCategoryId.activeCategory == category.id) R.drawable.expand_more else R.drawable.expand_more_less),
                contentDescription = "Expand/Collapse",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        if (isLongPressActive.value) {
            HandleLongPressDialog(
                onLongClick = isLongPressActive,
                category = category,
                viewModel = viewModel
            )
        }

        val state = rememberScrollState()
        LaunchedEffect(Unit) { state.animateScrollTo(100) }
        // Список элементов, отображаемых при развернутом состоянии
        if (expandedCategoryId.activeCategory == category.id) {
            Column(
                modifier = Modifier
                    .size(width = 300.dp, height = 150.dp)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(state)
            ) {
                items.forEach { item ->
                    item?.name?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .clickable { onItemClick(item.id) }
                                .padding(4.dp)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun HandleLongPressDialog(
    onLongClick: MutableState<Boolean>,
    category: Category?,
    viewModel: CategoryViewModel
) {
    if (onLongClick.value) {
        DialogWithImage(
            onDismissRequest = { onLongClick.value = false },
            onConfirmation = {
                category?.let { viewModel.deleteCategory(it.id) }
                onLongClick.value = false
            },
            painter = painterResource(id = R.drawable.cat_dialog),
            text = "Вы действительно хотите удалить выбранную категорию?",
            imageDescription = "cat"
        )
    }
}

@Composable
fun ProductsOfTheCategories(
    items: List<Inventory?>,
    category: Category,
    viewModel: CategoryViewModel,
    onClickExpandedItem: (String) -> Unit
) {
    val selectedItems = remember { mutableStateListOf<String>() }

    Column {
        ExpandableListItem(
            category = category,
            items = items.filter { it?.categoryId == category.id },
            onItemClick =
            {
                onClickExpandedItem(it)
            },
            onItemCategoryClick = {
                viewModel.activeCategory(it)
                viewModel.getInventoryByCategoryId(it)

            },
            viewModel
        )
        Log.d("selectedItems", "${selectedItems}")
    }
}

@Composable
private fun SnackBarToast(
    snackbarMessage: SnackbarMessage?, context: Context
) {
    snackbarMessage?.let { message ->
        Log.d("snack", "${message}")
        Snackbar(modifier = Modifier.padding(8.dp), actionOnNewLine = true, dismissAction = {
            TextButton(onClick = { SnackbarManager.clearSnackbarState() }) {
                Text(text = "Закрыть", color = colorResource(id = R.color.AdamantineBlue))
            }
        }) {
            Text(message.toMessage(context.resources), fontSize = 12.sp)
        }

    }
}


