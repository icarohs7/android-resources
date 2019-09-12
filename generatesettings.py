import os

modules = [f for f in os.listdir(".") if f.endswith("resources")]
with open("_settings.gradle.kts", "w") as file:
    for module in modules:
        file.write(f"include(\":{module}\")\n")
    file.write("\nsetKotlinPluginsVersion()\n")
