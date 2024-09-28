package cn.rtast.antiprinter

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class AntiPrinter : ModInitializer {

    private fun isFacingBlock(player: PlayerEntity, blockPos: BlockPos): Boolean {
        val playerLookDirection: Direction = player.horizontalFacing
        val blockDirection: Direction = getBlockDirection(player, blockPos)
        return playerLookDirection == blockDirection
    }

    private fun getBlockDirection(player: PlayerEntity, blockPos: BlockPos): Direction {
        val playerPos = player.blockPos
        val dx = blockPos.x - playerPos.x
        val dz = blockPos.z - playerPos.z
        return Direction.getFacing(dx.toFloat(), 0.0f, dz.toFloat())
    }

    override fun onInitialize() {
        UseBlockCallback.EVENT.register(UseBlockCallback { player, _, _, hitResult ->
            val blockPos = hitResult.blockPos
            if (!isFacingBlock(player, blockPos)) {
                return@UseBlockCallback ActionResult.FAIL
            }
            ActionResult.PASS
        })
    }
}
