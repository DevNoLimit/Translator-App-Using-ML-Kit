package com.devnolimit.translatorusinmlkit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.Language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var userInput by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("") }
    var showDropDown by remember { mutableStateOf(false) }
    var outputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = userInput,
            onValueChange = {
                userInput = it
            },
            placeholder = {
                Text("Enter your text")
            },
            singleLine = false,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(10.dp))

        //Drop Down
        ExposedDropdownMenuBox(
            expanded = showDropDown,
            onExpandedChange = {
                showDropDown = !showDropDown
            }
        ) {
            OutlinedTextField(
                value = selectedLanguage,
                onValueChange = {},
                placeholder = {
                    Text("Select translation language")
                },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                readOnly = true,
                maxLines = 3,
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = showDropDown,
                onDismissRequest = {
                    showDropDown = false
                },
                modifier = Modifier.fillMaxWidth().height(250.dp)
            ) {
                LanguagesUtils.getAllLanguagesCode().forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(item, modifier = Modifier.fillMaxWidth())
                        },
                        onClick = {
                            selectedLanguage = item
                            showDropDown = false
                        }
                    )
                }
            }
        }

        //Spacer
        Spacer(Modifier.size(10.dp))

        //Button
        Button(onClick = {
            isLoading = true
            LanguagesUtils.translationInit(userInput, selectedLanguage){
                isLoading = false
                outputText = this
            }
        },modifier = Modifier.fillMaxWidth()) {
            Text("Translate")
        }

        //Spacer
        Spacer(Modifier.size(10.dp))

        //Progress Bar
        AnimatedVisibility(
            visible = isLoading
        ) {
            CircularProgressIndicator()
        }

        //Output
        AnimatedVisibility(
            visible = outputText.isNotEmpty()
        ) {
            Text(outputText,modifier = Modifier.fillMaxWidth())
        }
    }
}