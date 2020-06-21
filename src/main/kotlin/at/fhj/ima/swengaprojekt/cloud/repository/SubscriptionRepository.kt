package at.fhj.ima.swengaprojekt.cloud.repository

import at.fhj.ima.swengaprojekt.cloud.entity.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepository : JpaRepository<Subscription, Int> {
}
