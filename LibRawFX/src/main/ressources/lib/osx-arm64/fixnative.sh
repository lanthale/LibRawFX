#!/bin/bash
set -e
cd "$(dirname "$0")"
shopt -s nullglob
dylibs=( *.dylib )

# ... (Schritte 2-4 wie gehabt: IDs setzen, Verweise umbiegen, prüfen) ...

# 5. Signieren – robust: Fehler unterbrechen die Schleife nicht
dot_clean -m . 2>/dev/null || true
failed=()
for f in "${dylibs[@]}"; do
  # Detritus beseitigen (auch com.apple.provenance)
  xattr -c "$f" 2>/dev/null || true
  if ! codesign --force --sign - "$f" 2>/dev/null; then
    # Erneuter Versuch nach weiterem Aufräumen
    xattr -d com.apple.provenance "$f" 2>/dev/null || true
    dot_clean -m "$f" 2>/dev/null || true
    if ! codesign --force --sign - "$f"; then
      echo "SIGNIEREN FEHLGESCHLAGEN: $f"
      failed+=( "$f" )
      continue
    fi
  fi
  if codesign -vv "$f" >/dev/null 2>&1; then
    echo "OK: $f"
  else
    echo "SIGNATUR UNGÜLTIG: $f"
    failed+=( "$f" )
  fi
done

echo ""
if [ ${#failed[@]} -eq 0 ]; then
  echo "Alle ${#dylibs[@]} Dateien erfolgreich signiert."
else
  echo "Fehlgeschlagen (${#failed[@]}):"
  printf '  %s\n' "${failed[@]}"
  exit 1
fi