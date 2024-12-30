# Jetpack Compose Demonstration

## Description
The Jetpack Compose Demonstration App is the application whose function is to show Jetpack Compose in action: its features, how to use it, and so on. This app is currently undergoing continuous updates.

## Technologies Used
*	Jetpack Compose
*	Mutable State
*	rememberSaveable
*	LazyColumn
*	rememberLazyListState
*	rememberCoroutineScope
*	Navigation Component
*	ViewModel
*	MaterialTheme

## Detailed Description
- The Jetpack Compose Demonstration App is showing two screens.
- The first screen is a scrollable list of items (LazyColumn).
- The second screen is for showing data from ViewModel and testing Navigation.
- For navigation purposes, the Navigation Component is used.
- To quickly move up to the top of the list, the rememberLazyListState is used and its method scrollToItem() is called.
- For saving states through configuration changes, rememberSaveable is used.
- The ViewModel is shared between two screens (two composables).
- The data is saving as StateFlow.

  <img src="https://github.com/user-attachments/assets/c8dc0f06-8e33-4a3a-a7eb-21d4589086fd" width="272" height="597">
  <img src="https://github.com/user-attachments/assets/38cc7688-b54a-4739-80b6-6525bd5ccc63" width="274" height="602">
  <img src="https://github.com/user-attachments/assets/ef0d6dd9-cc88-49b2-baa4-482ab2f839b1" width="275" height="602">
  <img src="https://github.com/user-attachments/assets/a960c916-a6e2-44b0-a8d9-59a40d2bafc9" width="273" height="597">
