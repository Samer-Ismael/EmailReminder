#!/bin/bash

APP_NAME="sending-emails"
JAR_NAME="SendingEmails-0.0.1-SNAPSHOT.jar"
INSTALL_DIR="/opt/$APP_NAME"
SERVICE_FILE="/etc/systemd/system/$APP_NAME.service"
JAR_FILE_PATH="./target/$JAR_NAME"  # JAR file is in the target directory

# Function to print messages
function print_message() {
    echo -e "\033[1;32m$1\033[0m"
}

# Check if running as root
if [ "$(id -u)" -ne 0 ]; then
    echo "Please run this script as root or using sudo."
    exit 1
fi

# Check if the JAR file exists in the specified path
if [ ! -f "$JAR_FILE_PATH" ]; then
    echo "JAR file $JAR_FILE_PATH not found. Please make sure the JAR file is at the specified location."
    exit 1
fi

print_message "Starting setup for $APP_NAME service..."

# Step 1: Create application directory
print_message "Creating application directory at $INSTALL_DIR..."
mkdir -p "$INSTALL_DIR"

# Step 2: Move the JAR file to the installation directory
print_message "Moving JAR file to $INSTALL_DIR..."
mv "$JAR_FILE_PATH" "$INSTALL_DIR/"

# Step 3: Create systemd service file
print_message "Creating systemd service file at $SERVICE_FILE..."
cat <<EOL >"$SERVICE_FILE"
[Unit]
Description=$APP_NAME Spring Boot Application
After=syslog.target
After=network.target

[Service]
User=$(whoami)
Group=$(whoami)
ExecStart=/usr/bin/java -jar $INSTALL_DIR/$JAR_NAME
SuccessExitStatus=143
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
EOL

# Step 4: Reload systemd and enable the service
print_message "Reloading systemd daemon..."
systemctl daemon-reload

print_message "Enabling $APP_NAME service to start on boot..."
systemctl enable "$APP_NAME"

# Step 5: Start the service
print_message "Starting $APP_NAME service..."
systemctl start "$APP_NAME"

print_message "Setup complete! Service $APP_NAME is running."
print_message "To check the status, use: systemctl status $APP_NAME"
print_message "To view logs, use: journalctl -u $APP_NAME -f"
