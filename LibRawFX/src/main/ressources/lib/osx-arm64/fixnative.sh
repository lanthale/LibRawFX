#!/bin/bash
set -e
cd "$(dirname "$0")"
shopt -s nullglob

dylibs=( *.dylib )

for f in "${dylibs[@]}"; do
  install_name_tool -id "@loader_path/$f" "$f"
done

for f in "${dylibs[@]}"; do
  deps=$(otool -L "$f" | tail -n +2 | awk '{print $1}')
  for dep in $deps; do
    case "$dep" in
      */opt/homebrew/*|*/usr/local/*|@rpath/*)
        install_name_tool -change "$dep" "@loader_path/$(basename "$dep")" "$f"
        ;;
    esac
  done
done

for f in "${dylibs[@]}"; do
  echo "== $f"; otool -L "$f" | tail -n +2
done

# 5. Fork-/Metadaten-Reste beseitigen und signieren
dot_clean -m .
for f in "${dylibs[@]}"; do
  xattr -c "$f"
  codesign --force --sign - "$f"
  codesign -vv "$f" >/dev/null || { echo "FEHLER beim Signieren von $f"; exit 1; }
  echo "OK: $f"
done

dot_clean -m .
for f in "${dylibs[@]}"; do
  xattr -c "$f"
  codesign --force --sign - "$f"
  codesign -vv "$f" >/dev/null || { echo "FEHLER beim Signieren von $f"; exit 1; }
  echo "OK: $f"
done

echo "Alle Dateien erfolgreich signiert."