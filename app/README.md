# Diario de Recuerdos

Diario de Recuerdos es una aplicación para registrar y gestionar tus recuerdos personales. Permite agregar y editar recuerdos con títulos, imágenes y ubicaciones. Además, la aplicación envía notificaciones locales cuando se agregan o actualizan los recuerdos.

## Características

- **Agregar Recuerdos:** Crea nuevos recuerdos con un título, imagen y ubicación.
- **Editar Recuerdos:** Actualiza la información de tus recuerdos existentes.
- **Tomar Fotos:** Captura imágenes directamente desde la aplicación.
- **Agregar Multimedia:** Selecciona imágenes desde la galería de tu dispositivo.
- **Ubicación:** Adjunta la ubicación actual al recuerdo.
- **Notificaciones:** Recibe notificaciones locales cuando se agregan o actualizan recuerdos.

## Videos y imagenes de funcionamiento

> Añade aquí algunas capturas de pantalla de la aplicación para mostrar su funcionalidad.

## Tecnologías Utilizadas

- **Kotlin**
- **Jetpack Compose** para la UI moderna y reactiva.
- **Android Jetpack:** ViewModel, LiveData, Navigation.
- **Google Accompanist:** Manejo de permisos y otras utilidades.
- **FusedLocationProviderClient:** Para obtener la ubicación actual del dispositivo.
- **NotificationCompat:** Para enviar notificaciones locales.

## Instalación y Configuración

1. **Clonar el Repositorio:**
   ```bash
   git clone https://github.com/tuusuario/diario-de-recuerdos.git
   cd diario-de-recuerdos
   ```

2. **Abrir el Proyecto en Android Studio:**
    - Importa el proyecto en Android Studio.
    - Sincroniza el proyecto con los archivos `gradle`.

3. **Configurar Firebase (Opcional):**
    - Si utilizas Firebase, añade el archivo `google-services.json` en el directorio `app/`.

4. **Compilar e Instalar la Aplicación:**
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

