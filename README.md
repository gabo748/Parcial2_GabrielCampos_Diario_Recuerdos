# Diario de Recuerdos

Diario de Recuerdos es una aplicación para registrar y gestionar tus recuerdos personales. Permite agregar y editar recuerdos con títulos, imágenes y ubicaciones. Además, la aplicación envía notificaciones locales cuando se agregan o actualizan los recuerdos.

## Características

- **Agregar Recuerdos:** Crea nuevos recuerdos con un título, imagen y ubicación.
- **Editar Recuerdos:** Actualiza la información de tus recuerdos existentes.
- **Tomar Fotos:** Captura imágenes directamente desde la aplicación.
- **Agregar Multimedia:** Selecciona imágenes desde la galería de tu dispositivo.
- **Ubicación:** Adjunta la ubicación actual al recuerdo.
- **Notificaciones:** Recibe notificaciones locales cuando se agregan o actualizan recuerdos.

## Capturas de Pantalla y Videos

- **Agregando todos los a la vez permisos**
https://github.com/user-attachments/assets/0d01e3af-1730-4453-bf7a-901851ea1f58

- **Solicitando Permisos multimedia** (Solo una vez)
https://github.com/user-attachments/assets/3035c09e-295e-482e-9207-f283b2933748

- **Solicitando permisos de camara** (Solo una vez)
https://github.com/user-attachments/assets/7a9b17a6-9fba-4624-848a-e6bb36020f62

- **Solicitando permisos ubicacion** (solo una vez)
https://github.com/user-attachments/assets/480adb73-bb76-4dd1-b07f-45a24dfd39c0

- **Mostrando notificacion de la aplicacion**
https://github.com/user-attachments/assets/8e12bece-0e83-40d2-86cb-a0cdbcd5130e


## Tecnologías Utilizadas

- **Kotlin**
- **Jetpack Compose** para la UI moderna y reactiva.
- **Android Jetpack:** ViewModel, LiveData, Navigation.
- **Google Accompanist:** Manejo de permisos y otras utilidades.
- **FusedLocationProviderClient:** Para obtener la ubicación actual del dispositivo.
- **NotificationCompat:** Para enviar notificaciones locales.


## Instalación y Configuración

1. **Clonar el Repositorio:**

2. **Abrir el Proyecto en Android Studio:**
   - Importa el proyecto en Android Studio.
   - Sincroniza el proyecto con los archivos `gradle`.

3. **Compilar e Instalar la Aplicación:**
   - Conecta un dispositivo o emulador y ejecuta la aplicación desde Android Studio.

## Permisos

La aplicación solicita los siguientes permisos:

- **Cámara:** Para capturar fotos y adjuntarlas a los recuerdos.
- **Almacenamiento:** Para seleccionar imágenes de la galería.
- **Ubicación:** Para agregar la ubicación actual al recuerdo.
- **Notificaciones:** Para enviar notificaciones locales sobre los cambios en los recuerdos.

## Estructura del Proyecto

- **`data`**: Contiene las clases del modelo de datos.
- **`ui/screens`**: Contiene las pantallas principales de la aplicación, como `HomeScreen` y `DetalleRecuerdoScreen`.
- **`viewModel`**: Contiene los ViewModels utilizados para gestionar el estado de la UI y la lógica de negocio.

