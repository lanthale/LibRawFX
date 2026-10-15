#!/bin/bash
for f in *.so *.so.*; do
  [ -L "$f" ] && continue
  patchelf --set-rpath '$ORIGIN' "$f"
done
