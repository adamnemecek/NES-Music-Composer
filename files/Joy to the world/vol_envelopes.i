volume_envelopes:
    .word ve_1
	.word ve_2
	.word ve_3
    
ve_1:
    .byte $09, $09, $09, $09, $0A, $0A, $0A, $0A
	.byte $0B, $0B, $0B, $0B, $0B, $0B, $0B, $0B
    .byte $0B, $0B, $0B, $0B, $0B, $0B, $0B, $0B
	.byte $0A, $0A, $0A, $0A, $0A, $0A, $0A, $0A
	.byte $09, $09, $09, $09, $08, $08, $08, $08
	.byte $07, $07, $06, $06, $05, $05, $04, $04
	.byte $03, $02, $01, $00
    .byte $FF
ve_2:
    .byte $09, $09, $09, $09, $0A, $0A, $0A, $0A
	.byte $0B, $0B, $0B, $0B, $0B, $0B, $0B, $0B
    .byte $0B, $0B, $0B, $0B, $0B, $0B, $0B, $0B
	.byte $0A, $0A, $0A, $0A, $0A, $0A, $0A, $0A
	.byte $09, $09, $09, $09, $08, $08, $08, $08
	.byte $07, $07, $06, $06, $05, $05, $04, $04
	.byte $03, $02, $01, $00
    .byte $FF
ve_3:
    .byte $09, $09, $09, $09, $0A, $0A, $0A, $0A
	.byte $0B, $0B, $0B, $0B, $0B, $0B, $0B, $0B
    .byte $0B, $0B, $0B, $0B, $0B, $0B, $0B, $0B
	.byte $0A, $0A, $0A, $0A, $0A, $0A, $0A, $0A
	.byte $09, $09, $09, $09, $08, $08, $08, $08
	.byte $07, $07, $06, $06, $05, $05, $04, $04
	.byte $03, $02, $01, $00
    .byte $FF
    
ve_voice_one = $00
ve_voice_two = $01
ve_voice_five = $02

