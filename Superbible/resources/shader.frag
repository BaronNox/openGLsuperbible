#version 450 core

out vec4 color;

layout (location = 100) uniform vec4 vColor;

void main() {
	color = vColor;
}
